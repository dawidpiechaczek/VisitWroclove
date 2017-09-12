package com.example.dawid.visitwroclove.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.adapter.CarouselAdapter;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.view.interfaces.MapView;
import java.util.List;

/**
 * Created by Dawid on 24.07.2017.
 */

public class MapPresenter extends BasePresenter<MapView> {
    private ObjectDAOImpl mRepoObjects;
    private EventDAOImpl mRepoEvents;
    private RouteDAOImpl mRepoRoutes;
    private List<ObjectDTO> buildings;
    private List<EventDTO> events;
    private RouteDTO routeDTO;
    private Context context;
    private RecyclerView recyclerView;

    public MapPresenter(Context context, ObjectDAOImpl mRepoObjects, EventDAOImpl mRepoEvents, RouteDAOImpl mRepoRoutes) {
        this.mRepoObjects = mRepoObjects;
        this.mRepoEvents = mRepoEvents;
        this.mRepoRoutes = mRepoRoutes;
        this.context = context;
    }

    public void initRepositories(int routeId) {
        buildings = mRepoObjects.getAll();
        events = mRepoEvents.getAll();
        if (routeId != -1)
            routeDTO = mRepoRoutes.getById(routeId);
    }

    public void setObjectsOnMap() {
        for (ObjectDTO o : buildings) {
            getView().addMarker(o, "OBJECT");
        }
    }

    public void setEventsOnMap() {
        for (EventDTO e : events) {
            getView().addMarker(e, "EVENT");
        }
    }

    public void setRouteOnMap() {
        if (routeDTO != null) {
            GoogleDirection.withServerKey("AIzaSyCd96x4S9MNuOSaH9-uKmnKgto3wc_qV4E")
                    .from(routeDTO.getLatLngsList().get(0))
                    .to(routeDTO.getLatLngsList().get(routeDTO.getLatLngsList().size() - 1))
                    .transitMode(TransportMode.WALKING)
                    .waypoints(routeDTO.getLatLngsList().subList(1, routeDTO.getLatLngsList().size() - 1))
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            getView().positiveRouteCallback(direction);
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            getView().negativeRouteCallback();
                        }
                    });

            setCarouselToMap(routeDTO.getPoints());
            addRecyclerSettings();
        }
    }

    private void addRecyclerSettings() {
        setLayoutManager();
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new CenterScrollListener());
    }

    private void setCarouselToMap(List points) {
        CarouselAdapter adapter = new CarouselAdapter(context, mRepoObjects, mRepoEvents);
        adapter.setData(points);
        adapter.setOnClickListener(new CarouselAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                getView().setCameraPosition(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutManager() {
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setMaxVisibleItems(3);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public RouteDTO getRoute() {
        return routeDTO;
    }
}
