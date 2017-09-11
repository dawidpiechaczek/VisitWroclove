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
import com.akexorcist.googledirection.util.DirectionConverter;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
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
    @Inject
    public ObjectDAOImpl mRepo;
    @Inject
    public RouteDAOImpl mRepoRoute;
    @Inject
    public EventDAOImpl mRepoEvents;
    @BindView(R.id.am_ll_container)
    public RelativeLayout container;

    public GoogleMap map;
    private Map<Marker, Integer> markersId = new HashMap<>();
    private List<ObjectDTO> buildings;
    private List<EventDTO> events;
    private RouteDTO routeDTO;

    int routeId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        buildings = mRepo.getAll();
        events = mRepoEvents.getAll();
        if (getIntent().getExtras() != null)
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
            marker.setTag("OBJECT");
            markersId.put(marker, o.getId());
        }

        for (EventDTO e : events) {
            LatLng latlng = new LatLng(Double.parseDouble(e.getAddress().getLat()), Double.parseDouble(e.getAddress().getLng()));
            Marker marker = map.addMarker(new MarkerOptions().position(latlng).title(e.getName()).title(e.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            marker.setTag("EVENT");
            markersId.put(marker, e.getId());
        }

        if (routeDTO != null){
            GoogleDirection.withServerKey("AIzaSyCd96x4S9MNuOSaH9-uKmnKgto3wc_qV4E")
                    .from(routeDTO.getLatLngsList().get(0))
                    .to(routeDTO.getLatLngsList().get(routeDTO.getLatLngsList().size() - 1))
                    .transitMode(TransportMode.DRIVING)
                    .waypoints(routeDTO.getLatLngsList().subList(1, routeDTO.getLatLngsList().size() - 1))
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
            addCarousel(routeDTO.getPoints());
        }

        MyWindowAdapter adapter = new MyWindowAdapter(getApplicationContext(), mRepo, mRepoEvents, markersId);
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
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(buildings.get(0).getAddress().getLat()), Double.parseDouble(buildings.get(0).getAddress().getLng())), 15));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "brak neta", Toast.LENGTH_LONG).show();
        } else {
            map.setMyLocationEnabled(true);
        }
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

    private void addCarousel(List<PointDTO>points){
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setMaxVisibleItems(3);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.am_rv_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        CarouselAdapter adapterek = new CarouselAdapter(this, mRepo);
        adapterek.setData(points);
        adapterek.setOnClickListener(new CarouselAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
               map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(routeDTO.getPoints().get(position).getLat()), Double.parseDouble(routeDTO.getPoints().get(position).getLng())), 15));
            }
        });
        recyclerView.setAdapter(adapterek);
        recyclerView.addOnScrollListener(new CenterScrollListener());
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}
