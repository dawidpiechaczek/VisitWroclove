package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.view.interfaces.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Dawid on 02.07.2017.
 */

public class MapActivity extends BaseActivity implements OnMapReadyCallback, MapView{
    public GoogleMap map;
    @Inject
    public ObjectDAOImpl mRepo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        initMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        List<ObjectDTO>buildings = mRepo.getAll();
        LatLng ratusz = new LatLng(Double.parseDouble(buildings.get(0).getAddress().getLat()),Double.parseDouble(buildings.get(0).getAddress().getLng()));
        LatLng hala= new LatLng(Double.parseDouble(buildings.get(1).getAddress().getLat()),Double.parseDouble(buildings.get(1).getAddress().getLng()));
        map.addMarker(new MarkerOptions().position(ratusz).title(buildings.get(0).getName()));
        map.addMarker(new MarkerOptions().position(hala).title(buildings.get(1).getName()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ratusz,15));
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}
