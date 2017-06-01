package com.ryanst.app.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Captcha {
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private int paddingLeft, paddingTop;
    private StringBuilder sbCode = new StringBuilder();
    private Random random = new Random();
    private Builder builder;

    private Captcha(Builder builder) {
        this.builder = builder;
    }

    private String code;
    private Bitmap image;

    public Bitmap refresh() {
        paddingLeft = 0; //每次生成验证码图片时初始化
        paddingTop = 0;

        image = Bitmap.createBitmap(builder.width, builder.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);

        //生成的验证码
        code = createCode();

        canvas.drawColor(Color.rgb(builder.bgColor, builder.bgColor, builder.bgColor));
        Paint paint = new Paint();
        paint.setTextSize(builder.fontSize);

        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding(i);
            canvas.drawText(code.charAt(i) + "", paddingLeft, paddingTop, paint);
        }

        //干扰线
        for (int i = 0; i < builder.lineNumber; i++) {
            drawLine(canvas, paint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);//保存
        canvas.restore();
        return image;
    }

    //生成验证码
    private String createCode() {
        sbCode.delete(0, sbCode.length()); //使用之前首先清空内容

        for (int i = 0; i < builder.codeLength; i++) {
            sbCode.append(CHARS[random.nextInt(CHARS.length)]);
        }

        return sbCode.toString();
    }

    public String getCode() {
        return code;
    }

    public Bitmap getImage() {
        return image;
    }

    //生成干扰线
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(builder.width);
        int startY = random.nextInt(builder.height);
        int stopX = random.nextInt(builder.width);
        int stopY = random.nextInt(builder.height);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    //随机颜色
    private int randomColor() {
        sbCode.delete(0, sbCode.length()); //使用之前首先清空内容

        String haxString;
        for (int i = 0; i < 3; i++) {
            haxString = Integer.toHexString(random.nextInt(0xFF));
            if (haxString.length() == 1) {
                haxString = "0" + haxString;
            }

            sbCode.append(haxString);
        }

        return Color.parseColor("#" + sbCode.toString());
    }

    //随机文本样式
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());  //true为粗体，false为非粗体
        float skewX = random.nextInt(5) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
    }

    //随机间距
    private void randomPadding(int index) {
        if (index == 0) {
            paddingLeft += random.nextInt(builder.basePaddingLeft);
        } else {
            paddingLeft += builder.basePaddingLeft + random.nextInt(builder.rangePaddingLeft);
        }
        paddingTop = builder.basePaddingTop + random.nextInt(builder.rangePaddingTop);
    }

    public static class Builder {
        private static final int DEFAULT_CODE_LENGTH = 4;//验证码的长度  这里是4位
        private static final int DEFAULT_FONT_SIZE = 60;//字体大小
        private static final int DEFAULT_LINE_NUMBER = 3;//多少条干扰线
        private static final int BASE_PADDING_LEFT = 70; //左边距
        private static final int RANGE_PADDING_LEFT = 30;//左边距范围值
        private static final int BASE_PADDING_TOP = 100;//上边距
        private static final int RANGE_PADDING_TOP = 40;//上边距范围值
        private static final int DEFAULT_WIDTH = 200;//默认宽度.图片的总宽
        private static final int DEFAULT_HEIGHT = 100;//默认高度.图片的总高
        private static final int DEFAULT_COLOR = 0xDF;//默认背景颜色值

        private int codeLength = DEFAULT_CODE_LENGTH;//验证码的长度
        private int fontSize = DEFAULT_FONT_SIZE;//字体大小
        private int lineNumber = DEFAULT_LINE_NUMBER;//多少条干扰线
        private int basePaddingLeft = BASE_PADDING_LEFT; //左边距
        private int rangePaddingLeft = RANGE_PADDING_LEFT;//左边距范围值
        private int basePaddingTop = BASE_PADDING_TOP;//上边距
        private int rangePaddingTop = RANGE_PADDING_TOP;//上边距范围值
        private int width = DEFAULT_WIDTH;//默认宽度.图片的总宽
        private int height = DEFAULT_HEIGHT;//默认高度.图片的总高
        private int bgColor = DEFAULT_COLOR;//默认背景颜色值

        public Builder() {
        }

        public Builder setCodeLength(int codeLength) {
            this.codeLength = codeLength;
            return this;
        }

        public Builder setFontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
            return this;
        }

        public Builder setBasePaddingLeft(int basePaddingLeft) {
            this.basePaddingLeft = basePaddingLeft;
            return this;
        }

        public Builder setRangePaddingLeft(int rangePaddingLeft) {
            this.rangePaddingLeft = rangePaddingLeft;
            return this;
        }

        public Builder setBasePaddingTop(int basePaddingTop) {
            this.basePaddingTop = basePaddingTop;
            return this;
        }

        public Builder setRangePaddingTop(int rangePaddingTop) {
            this.rangePaddingTop = rangePaddingTop;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setBgColor(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public Captcha build() {
            Captcha captcha = new Captcha(this);
            captcha.refresh();
            return captcha;
        }
    }
}













