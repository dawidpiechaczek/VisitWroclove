package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.utils.FontManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.dawid.visitwroclove.utils.Constants.BUS_WEB_VIEW;
import static com.example.dawid.visitwroclove.utils.Constants.EXTRA_WEB_VIEW;
import static com.example.dawid.visitwroclove.utils.Constants.WEATHER_WEB_VIEW;


public class MainPanelActivity extends BaseActivity {

    private String mLog = MainPanelActivity.class.getName();
    @BindView(R.id.tv_map)TextView mpa_tv_map;
    @BindView(R.id.tv_events)TextView mpa_tv_events;
    @BindView(R.id.tv_places)TextView mpa_tv_places;
    @BindView(R.id.tv_tracks)TextView mpa_tv_tracks;
    @BindView(R.id.tv_bus)TextView mpa_tv_buses;
    @BindView(R.id.tv_weather)TextView mpa_tv_weather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        ButterKnife.bind(this);
        Log.d(mLog, "MainPanelActivity.onCreate()");
        setPermissions();
        setIcons();
    }

    private void setIcons() {
        mpa_tv_places.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_map.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_events.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_tracks.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_weather.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_buses.setTypeface(FontManager.getIcons(MainPanelActivity.this));
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(mLog, "MainPanelActivity.onResume()");
    }

    @OnClick(R.id.ll_map)
    public void showMapActivity() {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        intent.putExtra("own_route_mode", true); //run own route creator mode
        startActivity(intent);
    }

    @OnClick(R.id.ll_tracks)
    public void showTracksActivity() {
        Intent intent = new Intent(getApplicationContext(), RoutesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_weather)
    public void showWeatherWebView() {
        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
        intent.putExtra(EXTRA_WEB_VIEW, WEATHER_WEB_VIEW);
        startActivity(intent);
    }

    @OnClick(R.id.ll_bus)
    public void showBusWebView() {
        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
        intent.putExtra(EXTRA_WEB_VIEW, BUS_WEB_VIEW);
        startActivity(intent);
    }

    @OnClick(R.id.ll_places)
    public void showPlacesActivity() {
        Intent intent = new Intent(getApplicationContext(), PlacesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_events)
    public void showEventsActivity() {
        Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
        startActivity(intent);
    }
}
