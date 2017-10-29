package com.example.dawid.visitwroclove.view.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.presenter.MapPresenter;
import com.example.dawid.visitwroclove.utils.OnSaveFragmentCallback;
import com.example.dawid.visitwroclove.utils.WindowListener;
import com.example.dawid.visitwroclove.adapter.CarouselAdapter;
import com.example.dawid.visitwroclove.adapter.MyWindowAdapter;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.view.fragment.SaveDialogFragment;
import com.example.dawid.visitwroclove.view.interfaces.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dawid on 02.07.2017.
 */

public class MapActivity extends BaseActivity implements OnMapReadyCallback, MapView, OnSaveFragmentCallback {
    @Inject ObjectDAOImpl mRepo;
    @Inject RouteDAOImpl mRepoRoute;
    @Inject EventDAOImpl mRepoEvents;
    @BindView(R.id.am_ll_container) RelativeLayout container;
    @BindView(R.id.am_rv_recycler) RecyclerView recyclerView;
    @BindView(R.id.am_btn_save) Button buttonSave;
    private MapPresenter presenter;
    private Map<Marker, Integer> markersId = new HashMap<>();
    public GoogleMap map;
    private int routeId = -1;
    private String totalTime;
    private boolean ownRouteModeCreator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        getExtra();
        presenter = new MapPresenter(MapActivity.this, mRepo, mRepoEvents, mRepoRoute);
        presenter.initRepositories(routeId);
        presenter.setRecyclerView(recyclerView);
        initMap();
    }

    @Override
    public void onResume() {
        presenter.attachView(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        presenter.detachView();
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        presenter.setObjectsOnMap();
        presenter.setEventsOnMap();
        presenter.setRouteOnMap();
        setMapListenersAndAdapters();
        setCameraPosition(-1);
        checkPermssions();
    }

    @OnClick(R.id.am_btn_save)
    public void onSaveRoute() {
        showSaveDialog();
    }

    private void showSaveDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("save_fragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        SaveDialogFragment newFragment = SaveDialogFragment.newInstance(this);
        newFragment.show(ft, "save_fragment");
    }

    private void checkPermssions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        } else {
            map.setMyLocationEnabled(true);
        }
    }

    private void setMapListenersAndAdapters() {
        MyWindowAdapter adapter = new MyWindowAdapter(MapActivity.this, mRepo, mRepoEvents, markersId);
        adapter.setCreatorMode(ownRouteModeCreator);
        WindowListener windowListener = new WindowListener(this, markersId);
        registerForContextMenu(container);
        map.setInfoWindowAdapter(adapter);
        map.setOnInfoWindowClickListener(windowListener);
        map.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                if (ownRouteModeCreator) {
                    openContextMenu(container);
                    presenter.setMarkerIdAndTag(markersId.get(marker), marker.getTag().toString());
                    marker.hideInfoWindow();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                presenter.addOwnRoute();
                return true;
            case R.id.delete:
                presenter.deleteFromRoute();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void addMarker(BaseDTO baseDTO, String tag) {
        LatLng latlng = new LatLng(Double.parseDouble(baseDTO.getAddress().getLat()), Double.parseDouble(baseDTO.getAddress().getLng()));
        Marker marker;
        if (tag.equals(getString(R.string.events))) {
            marker = map.addMarker(new MarkerOptions().position(latlng).title(baseDTO.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        } else {
            marker = map.addMarker(new MarkerOptions().position(latlng).title(baseDTO.getName()));
        }
        marker.setTag(tag);
        markersId.put(marker, baseDTO.getId());
    }

    @Override
    public void setCameraPosition(int position) {
        if (position != -1) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(presenter.getRoute().getPoints().get(position).getLat()), Double.parseDouble(presenter.getRoute().getPoints().get(position).getLng())), 15));
        } else {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.109678, 17.031879), 15));
        }
    }

    @Override
    public void positiveRouteCallback(Direction direction) {
        if (direction.isOK()) {
            List<Leg> directionPositionList = direction.getRouteList().get(0).getLegList();
            int duration = 0;
            for (Leg leg : directionPositionList) {
                ArrayList<LatLng> latLngs = leg.getDirectionPoint();
                map.addPolyline(DirectionConverter.createPolyline(this, latLngs, 5, Color.RED));
                duration += Double.parseDouble(leg.getDistance().getValue());
            }
            totalTime = String.valueOf(duration / 1000.0) + " km";
        } else {
            Toast.makeText(this, getString(R.string.error_route), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void negativeRouteCallback() {
        Toast.makeText(this, getString(R.string.error_route), Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearPolylines() {
        map.clear();
    }

    @Override
    public void setButtonVisibility(boolean visible) {
        if (visible) {
            buttonSave.setVisibility(View.VISIBLE);
        } else {
            buttonSave.setVisibility(View.INVISIBLE);
        }
    }

    public void getExtra() {
        if (getIntent().getExtras() != null) {
            routeId = getIntent().getExtras().getInt("trasa", -1);
            ownRouteModeCreator = getIntent().getExtras().getBoolean("own_route_mode");
        }
    }

    @Override
    public void onSave(String name, String description, String type) {
        presenter.saveRoute(name, description, totalTime, type);
    }
}
