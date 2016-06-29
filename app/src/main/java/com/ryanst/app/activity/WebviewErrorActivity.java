package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.view.MyWebView;

/**
 * Created by zhengjuntong on 16/4/28.
 */
public class WebviewErrorActivity extends BaseSlideActivity {

    MyWebView wb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.webview_error);
//        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        dealWebView();
    }

    private void dealWebView() {
        wb = (MyWebView) findViewById(R.id.wb_test_error);
        wb.setWebViewClient(new MyWebView.MyWebViewClient());
    }

    public void onClick(View view) {
        String url = "http://test.mobile.haibian.com/v1/question/detail?app_sign=bdbba8a6a33e003aa959da6daacb779c&token=eyJpdiI6InhqdG92RGR5SjNnbjdBWDFqdEhmdWc9PSIsInZhbHVlIjoid3k0dldIS1daRW5IRmpDZ1Z6XC9UQm5WTU9KcUxKajJ1S2JFWHRBNFVlTXdCaWFZdXB5OEliTDlsd0R6ZzhncVAiLCJtYWMiOiJhY2ZhMWNiZWRjNDBiNjU2OWJkZDdhZTdhZmFiMmNlZjdhYjVjZDkzYzNkYzgxYzE4Yjg0OGEwMWRkNDdmNDg4In0=&app_id=android_app&uuid=00006529dafe1a724b";
        wb.loadUrl(url);
    }

}
