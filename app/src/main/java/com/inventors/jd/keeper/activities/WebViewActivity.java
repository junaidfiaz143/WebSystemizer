package com.inventors.jd.keeper.activities;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.inventors.jd.keeper.R;


public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    private ProgressBar progressBar;

    private RelativeLayout lytError;

    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

//        Configuration config = getResources().getConfiguration();

        progressBar = findViewById(R.id.progressBar);

        webView = findViewById(R.id.webView);

        lytError = findViewById(R.id.lytError);

        try {
            url = getIntent().getExtras().getString("url");
        } catch (Exception exp) {
            Toast.makeText(this, "No URL specified.", Toast.LENGTH_SHORT).show();
        }

        if (url != null)
            startWebView(url);

    }

    private void startWebView(String url) {

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Toast.makeText(WebViewActivity.this, url.contains("https://m.youtube.com") + "", Toast.LENGTH_SHORT).show();
//                if (url.contains("https://m.youtube.com")) {
////                    getDownloadableUrl(url.split("v=")[1]);
//                } else
//                    view.loadUrl(url);
                // This is my website, so do not override; let my WebView load the page
                return !"www.vinqel.com".equals(Uri.parse(url).getHost());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);

                if (!view.getTitle().equals("Webpage not available")) {
                    lytError.setVisibility(View.GONE);
                    lytError.setEnabled(false);

                    webView.setVisibility(View.VISIBLE);
                    webView.setEnabled(true);
                } else if (view.getTitle().equals("Webpage not available")) {
                    lytError.setVisibility(View.VISIBLE);
                    lytError.setEnabled(true);

                    webView.setVisibility(View.GONE);
                    webView.setEnabled(false);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(0);

                lytError.setVisibility(View.VISIBLE);
                lytError.setEnabled(true);

                webView.setVisibility(View.GONE);
                webView.setEnabled(false);
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    public void onRefreshWebView(View view) {
        if (url != null) {
            startWebView(url);
        }
    }
}
