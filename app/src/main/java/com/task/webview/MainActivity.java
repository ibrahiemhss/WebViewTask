package com.task.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    private final String url="https://github.com/ibrahiemhss";
    ProgressBar progressBar;
    ImageButton refresh,forward,back;
    FrameLayout containerActions;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        refresh = (ImageButton) findViewById(R.id.refresh);
        forward = (ImageButton) findViewById(R.id.forward);
        back = (ImageButton) findViewById(R.id.back);
        containerActions = (FrameLayout) findViewById(R.id.containerActions);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getPath());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());

        getWebView(url);

        refresh.setOnClickListener(v -> webView.loadUrl( url ));
        forward.setOnClickListener(v -> {
            Log.d("Actions_logs","on canGoForward = "+String.valueOf(webView.canGoForward()));

            if(webView.canGoForward())
                Log.d("Actions_logs","go forward");
            webView.goForward();

        });

        back.setOnClickListener(v -> {
            Log.d("Actions_logs","on canGoBack = "+String.valueOf(webView.canGoBack()));
            if(webView.canGoBack())
                Log.d("Actions_logs","go back");
            webView.goForward();
        });

    }

    public void getWebView(String myurl) {

        webView.setOnKeyListener((v, keyCode, event) -> {

                Log.d("Actions_logs","event Action :\n"+event.toString());

                        if(webView.canGoBack())
                        {
                            Log.d("Actions_logs","on back");
                          //  back.setVisibility(View.VISIBLE);
                          //  forward.setVisibility(View.GONE);
                            return true;
                        }else if(webView.canGoForward())
                        {
                            Log.d("Actions_logs","on forward");
                           // forward.setVisibility(View.VISIBLE);
                          //  back.setVisibility(View.GONE);
                            webView.goBack();
                            return true;

                }

            return false;
        });

        webView.setWebChromeClient(new WebChromeClient(){

            public void onProgressChanged(WebView view, int progress) {
                setTitle("Loading...");
                progressBar.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.GONE);
                progressBar.setProgress(progress);
                setProgress(progress * 100);

            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressBar.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.GONE);
                containerActions.setVisibility(View.GONE);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                containerActions.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageFinished(view, url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

        });
        webView.loadUrl(myurl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Log.d("Actions_logs","on canGoBack = "+String.valueOf(webView.canGoBack()));

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }
}