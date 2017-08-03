package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.WindowAdapter;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.view.interfaces.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

    public GoogleMap map;
    private Map<Marker, Integer> markersId = new HashMap<>();
    private List<ObjectDTO> buildings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        buildings = mRepo.getAll();
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

        WindowAdapter adapter = new WindowAdapter(getApplicationContext(), mRepo, (HashMap<Marker, Integer>) markersId);
        map.setInfoWindowAdapter(adapter);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(buildings.get(0).getAddress().getLat()), Double.parseDouble(buildings.get(0).getAddress().getLng())), 15));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}
