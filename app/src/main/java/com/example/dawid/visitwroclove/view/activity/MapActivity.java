package com.example.dawid.visitwroclove.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.dawid.visitwroclove.utils.WindowListener;
import com.example.dawid.visitwroclove.adapter.CarouselAdapter;
import com.example.dawid.visitwroclove.adapter.MyWindowAdapter;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.view.interfaces.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 02.07.2017.
 */

public class MapActivity extends BaseActivity implements OnMapReadyCallback, MapView {
    @Inject ObjectDAOImpl mRepo;
    @Inject RouteDAOImpl mRepoRoute;
    @Inject EventDAOImpl mRepoEvents;
    @BindView(R.id.am_ll_container) RelativeLayout container;
    @BindView(R.id.am_rv_recycler) RecyclerView recyclerView;
    private MapPresenter presenter;
    private Map<Marker, Integer> markersId = new HashMap<>();
    public GoogleMap map;
    private int routeId = -1;

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

    private void checkPermssions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "brak neta", Toast.LENGTH_LONG).show();
        } else {
            map.setMyLocationEnabled(true);
        }
    }

    private void setMapListenersAndAdapters() {
        MyWindowAdapter adapter = new MyWindowAdapter(MapActivity.this, mRepo, mRepoEvents, markersId);
        WindowListener windowListener = new WindowListener(this, markersId);
        registerForContextMenu(container);
        map.setInfoWindowAdapter(adapter);
        map.setOnInfoWindowClickListener(windowListener);
        map.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                openContextMenu(container);
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.add:
                return true;
            case R.id.delete:
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
        if (tag.equals("EVENT")) {
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
            for (Leg leg : directionPositionList) {
                ArrayList<LatLng> latLngs = leg.getDirectionPoint();
                map.addPolyline(DirectionConverter.createPolyline(this, latLngs, 5, Color.RED));
            }
        } else {
            Toast.makeText(this, "Coś poszło nie tak", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void negativeRouteCallback() {
        Toast.makeText(this, "Coś poszło nie tak", Toast.LENGTH_LONG).show();
    }

    public void getExtra() {
        if (getIntent().getExtras() != null) {
            routeId = getIntent().getExtras().getInt("trasa", -1);
        }
    }
}
