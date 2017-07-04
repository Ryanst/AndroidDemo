package com.ryanst.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.ryanst.app.R;


public class WebActivity extends Activity {
    private String mCurrentUrl = "http://10.4.18.3:8020/plan_float/answerDetail.html?id=1ff9fe53bbb70f2dadb41b468f9f4b25&claId=40288b155c3e0dec015c3e16cb5b0015&cityCode=010&stuId=0000000049a958eb0149ac7263df018a&cOrder=5";
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

        mWeContent.loadUrl(mCurrentUrl);
    }
}
