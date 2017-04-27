package com.ryanst.app.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.ryanst.app.R;
import com.ryanst.app.util.X5WebviewUtil;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by zhengjuntong on 16/4/28.
 */
public class WebviewErrorActivity extends AppCompatActivity {
    ProgressBar webProgress;
    WebView wb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_error);
        wb = (WebView) findViewById(R.id.wb_test_error);
        webProgress = (ProgressBar) findViewById(R.id.webProgress);
        dealWebView();
    }

    private void dealWebView() {
        X5WebviewUtil.setWebViewSettings(
                this,
                wb,
                new X5WebviewUtil.JsListener() {
                    @Override
                    public void reload() {

                    }
                },
                new X5WebviewUtil.ProgressBarListener() {
                    @Override
                    public void hideProgressBar(boolean hide, int progress) {
                        if (webProgress != null) {
                            if (hide) {
                                if (webProgress.getVisibility() != View.GONE) {
                                    webProgress.setVisibility(View.GONE);
                                }
                            } else {
                                if (webProgress.getVisibility() != View.VISIBLE) {
                                    webProgress.setVisibility(View.VISIBLE);
                                }
                                webProgress.setProgress(progress);
                            }
                        }
                    }
                },
                true,
                new X5WebviewUtil.LoadWebViewProgressListener() {
                    @Override
                    public void onLoadFinish() {

                        String fetchJsAnswer = "javascript:$.practice.client.showMeAnswer();";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            wb.evaluateJavascript(fetchJsAnswer, new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {
                                }
                            });
                        } else {
                            wb.loadUrl(fetchJsAnswer);
                        }

                    }
                }
        );

        String url = "http://mobile.haibian.com/v1.2/practice/userAnswer?app_sign=f7a3dffa11f2aefb3477785f838da41e&taskId=19456&app_id=android_app&token=eyJpdiI6ImRCSmxjY1wvemNSYlVsNndCbmFJcGJnPT0iLCJ2YWx1ZSI6Ik9PcGlxU1BLUGpYcXo2Mk1Td2JoM3lISTc4MEtmRkpVbFpRR2xrRk42M3NxN2tOeXlEN29qS0FcL0tzek50eXRKIiwibWFjIjoiYmMzMjJjMjk4MDgwODVhNjc3Y2YwOGZmNDA0ZmEyMzUyNTY2MjYyNWQ5MDQ0ZjM4NzA4M2Q0NGM3ZDFlZmJiNCJ9&queId=b59caa1461f0496da1c8744dad20545b&paperId=1c00e1b0b9154aef94b29872e46bea1e&from=0";
        wb.loadUrl(url);
    }
}
