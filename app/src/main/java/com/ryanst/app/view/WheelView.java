package com.ryanst.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ryanst.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjuntong on 16/6/17.
 */

public class WheelView extends ScrollView {
    public static final int DEFAULT_UN_SELECT_TEXT_COLOR = Color.parseColor("#bbbbbb");
    public static final int DEFAULT_SELECT_TEXT_COLOR = Color.parseColor("#505050");
    public static final int DEFAULT_TEXT_SIZE = 13;
    public static final int DEFAULT_TEXT_PADDING = 12;
    public static final int DEFAULT_FLING_SPEED = 3;
    public static final int DEFAULT_INDEX = 0;
    public static final int DEFAULT_OFF_SET = 1;

    private int selectedTextSize = DEFAULT_TEXT_SIZE;
    private int textSize = DEFAULT_TEXT_SIZE;
    private int textPadding = DEFAULT_TEXT_PADDING;
    private int textColor = DEFAULT_UN_SELECT_TEXT_COLOR;
    private int offset = DEFAULT_OFF_SET; // 偏移量（需要在最前面和最后面补全）
    private int selectTextColor = DEFAULT_SELECT_TEXT_COLOR;
    private int flingSpeed = DEFAULT_FLING_SPEED;
    private int defaultIndex = DEFAULT_INDEX;

    private int initialY;
    private Runnable scrollerTask;
    private int newCheck = 50;
    private int itemHeight = 0;
    private int selectedIndex = 1; //index+offset

    private Context context;
    private LinearLayout rootView;
    private List<String> items;

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.wheel_view);
        textSize = (int) typedArray.getDimension(R.styleable.wheel_view_text_size, DEFAULT_TEXT_SIZE);
        selectedTextSize = textSize;
        textColor = typedArray.getColor(R.styleable.wheel_view_un_select_color, DEFAULT_UN_SELECT_TEXT_COLOR);
        selectTextColor = typedArray.getColor(R.styleable.wheel_view_select_color, DEFAULT_SELECT_TEXT_COLOR);
        offset = typedArray.getInteger(R.styleable.wheel_view_offset, DEFAULT_OFF_SET);
        defaultIndex = typedArray.getInteger(R.styleable.wheel_view_default_index, DEFAULT_INDEX);
        flingSpeed = typedArray.getInteger(R.styleable.wheel_view_fling_speed, DEFAULT_FLING_SPEED);
        selectedIndex = defaultIndex + offset;
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.setVerticalScrollBarEnabled(false);
        rootView = new LinearLayout(context);
        rootView.setOrientation(LinearLayout.VERTICAL);
        this.addView(rootView);
        initScrollTask();
    }

    private void initScrollTask() {
        scrollerTask = new Runnable() {
            public void run() {
                int newY = getScrollY();
                if (initialY - newY == 0) { // stopped
                    final int overDistance = initialY % itemHeight;
                    final int itemIndex = initialY / itemHeight;

                    if (overDistance == 0) {
                        selectedIndex = itemIndex + offset;
                        onSeletedCallBack();
                    } else if (overDistance > itemHeight / 2) {
                        WheelView.this.post(new Runnable() {
                            @Override
                            public void run() {
                                WheelView.this.smoothScrollTo(0, initialY - overDistance + itemHeight);
                                selectedIndex = itemIndex + offset + 1;
                                onSeletedCallBack();
                            }
                        });
                    } else {
                        WheelView.this.post(new Runnable() {
                            @Override
                            public void run() {
                                WheelView.this.smoothScrollTo(0, initialY - overDistance);
                                selectedIndex = itemIndex + offset;
                                onSeletedCallBack();
                            }
                        });
                    }
                } else {
                    initialY = getScrollY();
                    WheelView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> list) {
        if (null == items) {
            items = new ArrayList<String>();
        }
        items.clear();
        items.addAll(list);

        // 前面和后面补全
        for (int i = 0; i < offset; i++) {
            items.add(0, "");
            items.add("");
        }
        initData();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    private void initData() {
        for (String item : items) {
            rootView.addView(createTextView(item));
        }
        refreshItemTextView(0);
    }

    private TextView createTextView(String item) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setSingleLine(true);
        textView.setTextSize(textSize);
        textView.setText(item);
        textView.setGravity(Gravity.CENTER);
        int padding = dip2px(textPadding);
        textView.setPadding(padding, padding, padding, padding);
        if (itemHeight == 0) {
            itemHeight = getViewMeasuredHeight(textView);
            int displayItemCount = offset * 2 + 1;
            rootView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * displayItemCount));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
            this.setLayoutParams(new LinearLayout.LayoutParams(lp.width, itemHeight * displayItemCount));
        }
        return textView;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemTextView(t);
        if (t > oldt) {
            scrollDirection = SCROLL_DIRECTION_DOWN;
        } else {
            scrollDirection = SCROLL_DIRECTION_UP;
        }
    }

    private void refreshItemTextView(int y) {
        int index = y / itemHeight + offset;
        int overDistance = y % itemHeight;

        //超过一半取下一个
        if (overDistance > itemHeight / 2) {
            index++;
        }

        int childSize = rootView.getChildCount();
        for (int i = 0; i < childSize; i++) {
            TextView itemView = (TextView) rootView.getChildAt(i);
            if (itemView == null) {
                return;
            }
            if (index == i) {
                itemView.setTextSize(selectedTextSize);
                itemView.setTextColor(selectTextColor);
            } else {
                itemView.setTextColor(textColor);
            }
        }
    }

    /**
     * 选中回调
     */
    private void onSeletedCallBack() {
        if (null != onWheelViewListener) {
            onWheelViewListener.onSelected(selectedIndex, items.get(selectedIndex));
        }
    }

    //这里的index是真正数据list的index
    public void setSeletion(final int index) {
        selectedIndex = index + offset;
        this.post(new Runnable() {
            @Override
            public void run() {
                WheelView.this.smoothScrollTo(0, index * itemHeight);
            }
        });
    }

    public String getSeletedItem() {
        return items.get(selectedIndex);
    }

    public int getSeletedIndex() {
        return selectedIndex - offset;
    }

    @Override
    public void fling(int velocityY) {
        super.fling((int) ((float) velocityY * flingSpeed / 10));//使fling操作的时候scrollview滚动的速度减慢到原来的1/3
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    public void startScrollerTask() {
        initialY = getScrollY();
        this.postDelayed(scrollerTask, newCheck);
    }

    public static class OnWheelViewListener {
        public void onSelected(int selectedIndex, String item) {
        }
    }

    private OnWheelViewListener onWheelViewListener;

    public OnWheelViewListener getOnWheelViewListener() {
        return onWheelViewListener;
    }

    public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener) {
        this.onWheelViewListener = onWheelViewListener;
    }

    private int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getViewMeasuredHeight(View view) {
        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
        return view.getMeasuredHeight();
    }


    /**
     * 获取选中区域的边界
     */
    int[] selectedAreaBorder;

    private int[] obtainSelectedAreaBorder() {
        if (null == selectedAreaBorder) {
            selectedAreaBorder = new int[2];
            selectedAreaBorder[0] = itemHeight * offset;
            selectedAreaBorder[1] = itemHeight * (offset + 1);
        }
        return selectedAreaBorder;
    }


    private int scrollDirection = -1;
    private static final int SCROLL_DIRECTION_UP = 0;
    private static final int SCROLL_DIRECTION_DOWN = 1;

    Paint paint;
    int viewWidth;

    @Override
    public void setBackgroundDrawable(Drawable background) {

        if (viewWidth == 0) {
            viewWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        }

        if (null == paint) {
            paint = new Paint();
            paint.setColor(Color.parseColor("#c6c6c6"));
            paint.setStrokeWidth(dip2px(1f));
        }

        background = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawLine(viewWidth * 0, obtainSelectedAreaBorder()[0], viewWidth, obtainSelectedAreaBorder()[0], paint);
                canvas.drawLine(viewWidth * 0, obtainSelectedAreaBorder()[1], viewWidth, obtainSelectedAreaBorder()[1], paint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };


        super.setBackgroundDrawable(background);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        setBackgroundDrawable(null);
    }
}
