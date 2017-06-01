package com.ryanst.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.util.WebViewUtil;
import com.ryanst.app.view.MyWebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 16/6/24.
 */
public class ScrollViewListViewActivity extends BaseSlideActivity {
    @BindView(R.id.webview)
    MyWebView webview;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.btn_load_url)
    Button btnLoadUrl;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_list_view_test);
        ButterKnife.bind(this);
        initWebView();
        initListView();
    }

    private void initListView() {
        List<HashMap<String, String>> list = new ArrayList<>();
        String key1 = "test1";
        String key2 = "test2";

        HashMap<String, String> map = new HashMap<>();
        map.put(key1, "content1");
        map.put(key2, "content2");

        HashMap<String, String> map2 = new HashMap<>();
        map2.put(key1, "content21");
        map2.put(key2, "content22");

        list.add(map);
        list.add(map2);

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item_list_view_2, new String[]{key1, key2}, new int[]{R.id.tv_title, R.id.tv_sub_title});
        listView.setAdapter(adapter);

        setTotalHeightofListView(listView);
    }

    private void initWebView() {
        String url = "http://test.mobile.haibian.com/v1/question/detail?app_sign=bdbba8a6a33e003aa959da6daacb779c&token=eyJpdiI6InhqdG92RGR5SjNnbjdBWDFqdEhmdWc9PSIsInZhbHVlIjoid3k0dldIS1daRW5IRmpDZ1Z6XC9UQm5WTU9KcUxKajJ1S2JFWHRBNFVlTXdCaWFZdXB5OEliTDlsd0R6ZzhncVAiLCJtYWMiOiJhY2ZhMWNiZWRjNDBiNjU2OWJkZDdhZTdhZmFiMmNlZjdhYjVjZDkzYzNkYzgxYzE4Yjg0OGEwMWRkNDdmNDg4In0=&app_id=android_app&uuid=00006529dafe1a724b";
        WebViewUtil.setWebViewSettings(webview, null, null);
        webview.loadUrl(url);
    }

    public void onClick(View view) {
        String url = "http://jzb.com/z2017/jzzk11/index.html?utm_source=push_jzb&utm_campaign=20170408";
        WebViewUtil.setWebViewSettings(webview, null, null);
        webview.setWebViewClient(new MyWebViewClient(null));
        webview.loadUrl(url);
    }

    public class MyWebViewClient extends WebViewClient {
        private WebViewUtil.ProgressBarListener progressBarListener;

        public MyWebViewClient(WebViewUtil.ProgressBarListener progressBarListener) {
            this.progressBarListener = progressBarListener;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("https://api.jzb.com/webapp/app/index?url=")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return false;
            } else {
                view.loadUrl(url);
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            view.loadUrl("file:///android_asset/error_page.html");
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.loadUrl("file:///android_asset/error_page.html");
        }
    }

    public static void setTotalHeightofListView(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
