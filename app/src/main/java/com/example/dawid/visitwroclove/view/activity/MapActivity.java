package com.example.dawid.visitwroclove.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.WindowListener;
import com.example.dawid.visitwroclove.adapter.WindowAdapter;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.view.interfaces.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Dawid on 02.07.2017.
 */

public class MapActivity extends BaseActivity implements OnMapReadyCallback, MapView {
    @Inject
    public ObjectDAOImpl mRepo;

    @Inject
    public RouteDAOImpl mRepoRoute;

    public GoogleMap map;
    private Map<Marker, Integer> markersId = new HashMap<>();
    private List<ObjectDTO> buildings;
    private RouteDTO routeDTO;

    int routeId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        buildings = mRepo.getAll();
        routeId = getIntent().getExtras().getInt("trasa", -1);
        if (routeId != -1)
            routeDTO = mRepoRoute.getById(routeId);
        initMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        for (ObjectDTO o : buildings) {
            LatLng latlng = new LatLng(Double.parseDouble(o.getAddress().getLat()), Double.parseDouble(o.getAddress().getLng()));
            Marker marker = map.addMarker(new MarkerOptions().position(latlng).title(o.getName()));
            markersId.put(marker, o.getId());
        }

        if (routeDTO.getLatLngsList().size()>2)
            GoogleDirection.withServerKey("AIzaSyCd96x4S9MNuOSaH9-uKmnKgto3wc_qV4E")
                    .from(routeDTO.getLatLngsList().get(0))
                    .to(routeDTO.getLatLngsList().get(routeDTO.getLatLngsList().size() - 1))
                    .transitMode(TransportMode.DRIVING)
                    .waypoints(routeDTO.getLatLngsList().subList(1, routeDTO.getLatLngsList().size() - 2))
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            if (direction.isOK()) {
                                ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                                map.addPolyline(DirectionConverter.createPolyline(MapActivity.this, directionPositionList, 5, Color.RED));
                            } else {
                                // Do something
                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            // Do something
                        }
                    });
        else
            GoogleDirection.withServerKey("AIzaSyCd96x4S9MNuOSaH9-uKmnKgto3wc_qV4E")
                    .from(routeDTO.getLatLngsList().get(0))
                    .to(routeDTO.getLatLngsList().get(routeDTO.getLatLngsList().size() - 1))
                    .transitMode(TransportMode.DRIVING)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            if (direction.isOK()) {
                                ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                                map.addPolyline(DirectionConverter.createPolyline(MapActivity.this, directionPositionList, 5, Color.RED));
                            } else {
                                // Do something
                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            // Do something
                        }
                    });
        WindowAdapter adapter = new WindowAdapter(getApplicationContext(), mRepo, (HashMap<Marker, Integer>) markersId);
        map.setInfoWindowAdapter(adapter);
        WindowListener windowListener = new WindowListener(this, markersId);
        map.setOnInfoWindowClickListener(windowListener);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(buildings.get(0).getAddress().getLat()), Double.parseDouble(buildings.get(0).getAddress().getLng())), 15));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "brak neta", Toast.LENGTH_LONG).show();
        } else {
            map.setMyLocationEnabled(true);
        }

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}
