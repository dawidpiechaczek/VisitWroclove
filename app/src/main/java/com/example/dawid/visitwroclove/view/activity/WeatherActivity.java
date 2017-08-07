package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.dawid.visitwroclove.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 04.08.2017.
 */

public class WeatherActivity extends BaseActivity {
    @BindView(R.id.wa_wb_webView)
    WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        ButterKnife.bind(this);
        webView.loadUrl("https://weather.com/pl-PL");

    }
}
