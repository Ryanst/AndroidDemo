package com.ryanst.app.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

import com.ryanst.app.BuildConfig;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by kevin on 16/3/18.
 */
public class X5WebviewUtil {
    private static final int FULL_PERCENT = 100;

    /**
     * webview  设置方法
     *
     * @param webView
     * @param listener
     * @param progressBarListener
     */

    public static void setWebViewSettings(Activity activity, final WebView webView,
                                          final JsListener listener,
                                          final ProgressBarListener progressBarListener, boolean openImage, LoadWebViewProgressListener loadWebViewProgressListener) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setBlockNetworkImage(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.LOAD_DEFAULT);//.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (BuildConfig.DEBUG) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setScrollContainer(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.setTransitionGroup(true);
        }
        webView.setWebViewClient(new MyWebViewClient(progressBarListener, openImage, loadWebViewProgressListener));
        webView.addJavascriptInterface(new JsClient(activity, webView, listener), "js");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBarListener != null) {
                    progressBarListener.hideProgressBar(false, newProgress);
                }
            }
        });
    }


    public static class MyWebViewClient extends WebViewClient {
        private LoadWebViewProgressListener loadWebViewProgressListener = null;
        private ProgressBarListener progressBarListener;
        private boolean openImage;

        public MyWebViewClient(ProgressBarListener progressBarListener, boolean openImage) {
            this.progressBarListener = progressBarListener;
            this.openImage = openImage;
        }

        public MyWebViewClient(ProgressBarListener progressBarListener, boolean openImage, LoadWebViewProgressListener loadWebViewProgressListener) {
            this.progressBarListener = progressBarListener;
            this.openImage = openImage;
            this.loadWebViewProgressListener = loadWebViewProgressListener;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressBarListener != null) {
                progressBarListener.hideProgressBar(true, FULL_PERCENT);
            }
            if (openImage) {
                addImageClickListner(view);
            }
            LogUtil.i("url    " + url);

            if (loadWebViewProgressListener != null) {
                loadWebViewProgressListener.onLoadFinish();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.loadUrl("file:///android_asset/error_page.html");
            if (progressBarListener != null) {
                progressBarListener.hideProgressBar(true, FULL_PERCENT);
            }
        }
    }


    private static void addImageClickListner(WebView webView) {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName(\"img\"); " + "var imgs = new Array(objs.length); " + "for(var i=0;i<objs.length;i++)  "
                + "{" + "    imgs[i] = objs[i].src;" + "    objs[i].onclick=function()  " + "    {  " + "        window.js.openImage(this.src,imgs);  " + "    }  " + "}"
                + "})()");
    }

    public static class JsClient {
        private Activity context;
        private WebView webView;

        public JsClient(Activity ctx, WebView webView, JsListener listener) {
            this.context = ctx;
            this.webView = webView;
            this.listener = listener;

        }

        private JsListener listener;

//        public JsClient(JsListener listener) {
//            this.listener = listener;
//        }

        @JavascriptInterface
        public void reloadPage() {
            LogUtil.i("reload page ......");
            if (listener != null) {
                listener.reload();
            }
        }

        @JavascriptInterface
        public void resize(final float height) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                    webView.setLayoutParams(new LinearLayout.LayoutParams(context.getResources().getDisplayMetrics().widthPixels, (int) (height * context.getResources().getDisplayMetrics().density)));
                }
            });
        }

        @JavascriptInterface
        public void openImage(String src, String[] imgs) {
            Intent intent = new Intent();
            intent.putExtra("imageUrl", src);
//            ActivityHandler.toPreviewImageActivity(context, intent);
        }
    }

    public interface JsListener {
        void reload();

    }

    public interface ProgressBarListener {
        void hideProgressBar(boolean hide, int progress);
    }

    public interface LoadWebViewProgressListener {
        void onLoadFinish();
    }


}
