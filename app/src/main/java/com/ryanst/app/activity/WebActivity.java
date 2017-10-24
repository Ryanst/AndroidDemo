package com.ryanst.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.ryanst.app.R;
import com.ryanst.app.util.WebViewUtil;


public class WebActivity extends Activity {
    private String mCurrentUrl = "";
    private WebView mWeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWeContent = (WebView) findViewById(R.id.wv_content);
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            mCurrentUrl = url;
        }

        WebViewUtil.setWebViewSettings(mWeContent, null, null);

        mWeContent.loadUrl(mCurrentUrl);
    }
}
