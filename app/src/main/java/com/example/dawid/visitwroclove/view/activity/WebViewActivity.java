package com.example.dawid.visitwroclove.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dawid.visitwroclove.utils.Constants.EXTRA_WEB_VIEW;

/**
 * Created by Dawid on 04.08.2017.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.wa_wb_webView) WebView webView;
    @BindView(R.id.toolbar3) Toolbar toolbar;
    private String url, toolbarTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.weather_activity);
        ButterKnife.bind(this);
        getExtrasFromIntent();
        setToolbar();
        initWebView();
    }

    private void getExtrasFromIntent() {
        String webViewType = getIntent().getStringExtra(EXTRA_WEB_VIEW);
        if(webViewType.equals(Constants.BUS_WEB_VIEW)){
            url = "http://www.wroclaw.pl/rozklady-jazdy";
            toolbarTitle = "Rozkład jazdy";
        } else {
            url = "https://weather.com/pl-PL/pogoda/godzinowa/l/PLXX0029:1:PL";
            toolbarTitle = "Pogoda";
        }
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(activity, "Problem z internetem", Toast.LENGTH_SHORT).show();
            }
        });

        webView.loadUrl(url);
    }

    private void setToolbar() {
        toolbar.setTitle(toolbarTitle);
        toolbar.setTitleTextColor(getColor(R.color.secondaryToolbar));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
