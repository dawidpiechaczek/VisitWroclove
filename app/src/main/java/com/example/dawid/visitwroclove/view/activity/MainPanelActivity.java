package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;


import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.AddressDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.utils.FontManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.dawid.visitwroclove.utils.Constants.BUS_WEB_VIEW;
import static com.example.dawid.visitwroclove.utils.Constants.EXTRA_WEB_VIEW;
import static com.example.dawid.visitwroclove.utils.Constants.WEATHER_WEB_VIEW;


public class MainPanelActivity extends BaseActivity {

    private String mLog = MainPanelActivity.class.getName();
    @BindView(R.id.tv_map) public TextView mpa_tv_map;
    @BindView(R.id.tv_events) public TextView mpa_tv_events;
    @BindView(R.id.tv_places) public TextView mpa_tv_places;
    @BindView(R.id.tv_tracks) public TextView mpa_tv_tracks;
    @BindView(R.id.tv_bus) public TextView mpa_tv_buses;
    @BindView(R.id.tv_weather) public TextView mpa_tv_weather;
    @Inject public ObjectDAOImpl mRepo;
    @Inject public EventDAOImpl mRepoEvent;
    @Inject public RouteDAOImpl mRepoRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_panel);
        ButterKnife.bind(this);
        Log.d(mLog, "MainPanelActivity.onCreate()");
        scripts();
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

    private void scripts() {
        mRepoEvent = new EventDAOImpl();
        ObjectDTO objectDTO = new ObjectDTO();
        objectDTO.setId(0);
        objectDTO.setFavourite(true);
        objectDTO.setName("Ratusz Wrocławski");
        objectDTO.setDescription("Stary Ratusz we Wrocławiu – późnogotycki budynek na wrocławskim Rynku, jeden z najlepiej zachowanych historycznych ratuszy w Polsce, zarazem jeden z głównych zabytków architektonicznych Wrocławia.");
        objectDTO.setPhone("42 455 34 43");
        objectDTO.setRank(7.53);
        objectDTO.setType("building");
        objectDTO.setImage("https://upload.wikimedia.org/wikipedia/commons/5/52/2017_Ratusz_Staromiejski_we_Wroc%C5%82awiu_01.jpg");
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(0);
        addressDTO.setCity("Wrocław");
        addressDTO.setStreet("Rynek");
        addressDTO.setHomeNumber("50");
        addressDTO.setLat("51.109678");
        addressDTO.setLng("17.031879");
        objectDTO.setAddress(addressDTO);
        mRepo.add(objectDTO);

        ObjectDTO objectDTO1 = new ObjectDTO();
        objectDTO1.setId(1);
        objectDTO1.setFavourite(true);
        objectDTO1.setName("Hala Stulecia");
        objectDTO1.setDescription("Hala Stulecia (inna funkcjonująca nazwa: Hala Ludowa), to duży obiekt widowiskowo-sportowy położony w Parku Szczytnickim we Wrocławiu. Wzniesiony w latach 1911–1913 według projektu architektonicznego Maxa Berga. W 2006 roku Hala została wpisana na Listę Światowego Dziedzictwa Kulturalnego i Przyrodniczego UNESCO! ");
        objectDTO1.setPhone("42 455 34 43");
        objectDTO1.setRank(9.53);
        objectDTO1.setType("building");
        objectDTO1.setImage("https://upload.wikimedia.org/wikipedia/commons/0/06/Wroc%C5%82aw_-_Jahrhunderthalle1.jpg");
        AddressDTO addressDTO1 = new AddressDTO();
        addressDTO1.setId(1);
        addressDTO1.setCity("Wrocław");
        addressDTO1.setZipCode("51-618");
        addressDTO1.setStreet("Wystawowa");
        addressDTO1.setHomeNumber("1");
        addressDTO1.setLat("51.107222");
        addressDTO1.setLng("17.076944");
        objectDTO1.setAddress(addressDTO1);
        mRepo.add(objectDTO1);

        ObjectDTO objectDTO2 = new ObjectDTO();
        objectDTO2.setId(2);
        objectDTO2.setFavourite(false);
        objectDTO2.setName("ZOO");
        objectDTO2.setDescription("Wrocławskie ZOO ma przebogatą tradycję. To tu urodziły się pierwsze w historii ogrodów zoologicznych tapiry, a gorylica PUSSI na wiele dziesięcioleci pobiła wszelkie rekordy długości życia w warunkach sztucznych. Metody hodowlane i sposób ekspozycji zwierząt ulegają stałym zmianom.");
        objectDTO2.setPhone("42 455 34 43");
        objectDTO2.setRank(9.53);
        objectDTO2.setType("visit");
        objectDTO2.setImage("http://www.zoo.wroclaw.pl/zdc/wp-content/uploads/2016/09/logo-ZOO-WROCLAW.jpg");
        AddressDTO addressDTO2 = new AddressDTO();
        addressDTO2.setId(2);
        addressDTO2.setCity("Wrocław");
        addressDTO2.setStreet("Wróblewskiego");
        addressDTO2.setHomeNumber("1-5");
        addressDTO2.setZipCode("51-618");
        addressDTO2.setLat("51.105625");
        addressDTO2.setLng("17.071294");
        objectDTO2.setAddress(addressDTO2);
        mRepo.add(objectDTO2);

        ObjectDTO objectDTO3 = new ObjectDTO();
        objectDTO3.setId(3);
        objectDTO3.setFavourite(false);
        objectDTO3.setName("Galeria Dominikańska");
        objectDTO3.setDescription("Galeria Dominikańska – centrum handlowe zlokalizowane pomiędzy Placem Dominikańskim oraz ulicami: bł. Czesława i Oławską, w centralnej części Wrocławia, niedaleko Rynku.");
        objectDTO3.setPhone("42 455 34 43");
        objectDTO3.setRank(9.53);
        objectDTO3.setType("mall");
        objectDTO3.setImage("http://galeria-dominikanska.pl/files/gallery/fotom-2196.jpg");
        AddressDTO addressDTO3 = new AddressDTO();
        addressDTO3.setId(3);
        addressDTO3.setCity("Wrocław");
        addressDTO3.setStreet("Biskupin");
        addressDTO3.setHomeNumber("14");
        addressDTO3.setLat("51.108591");
        addressDTO3.setLng("17.040943");
        objectDTO3.setAddress(addressDTO3);
        mRepo.add(objectDTO3);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(0);
        eventDTO.setFavourite(true);
        eventDTO.setName("Wyścigi konne");
        eventDTO.setDescription("Wrocławski Tor Wyścigów Konnych - Partynice jest jednostką organizacyjną Gminy Wrocław, działającą w formie jednostki budżetowej, nie posiada osobowości prawnej. Leży na terenie osiedla Partynice.");
        eventDTO.setType("sport");
        eventDTO.setImage("http://www.iceis.pl/wyscigi/konne/wyscigi-konne_partynice,400px.jpg");
        eventDTO.setPrice("15 zł");
        eventDTO.setStartDate("15.04.2018");
        AddressDTO addressDTO4 = new AddressDTO();
        addressDTO4.setId(4);
        addressDTO4.setCity("Wrocław");
        addressDTO4.setStreet("Zwycięska");
        addressDTO4.setHomeNumber("2");
        addressDTO4.setZipCode("53-033");
        addressDTO4.setLat("51.060935");
        addressDTO4.setLng("16.997813");
        eventDTO.setAddress(addressDTO4);
        mRepoEvent.add(eventDTO);

        EventDTO eventDTO1 = new EventDTO();
        eventDTO1.setId(1);
        eventDTO1.setFavourite(false);
        eventDTO1.setName("Koncert wrocławski");
        eventDTO1.setDescription("Wrocławski Tor Wyścigów Konnych - Partynice jest jednostką organizacyjną Gminy Wrocław, działającą w formie jednostki budżetowej, nie posiada osobowości prawnej. Leży na terenie osiedla Partynice.");
        eventDTO1.setType("event");
        eventDTO1.setImage("http://ocdn.eu/pulscms-transforms/1/kqIktkpTURBXy9iYjM4MDJkOThmMjcyOTk3OGYyYTIyMWRlZTg2NWRmZC5qcGeSlQMBAM0IF80EjZMFzQNSzQHe");
        eventDTO1.setPrice("50 zł");
        eventDTO1.setStartDate("20.04.2018");
        AddressDTO addressDTO5 = new AddressDTO();
        addressDTO5.setId(5);
        addressDTO5.setCity("Wrocław");
        addressDTO5.setStreet("Robotnicza");
        addressDTO5.setHomeNumber("2");
        addressDTO5.setZipCode("52-443");
        addressDTO5.setLat("51.106606");
        addressDTO5.setLng("17.028531");
        eventDTO1.setAddress(addressDTO5);
        mRepoEvent.add(eventDTO1);

        EventDTO eventDTO2 = new EventDTO();
        eventDTO2.setId(2);
        eventDTO2.setFavourite(false);
        eventDTO2.setName("Mecz Sląska Wrocław");
        eventDTO2.setDescription("Wrocławski Tor Wyścigów Konnych - Partynice jest jednostką organizacyjną Gminy Wrocław, działającą w formie jednostki budżetowej, nie posiada osobowości prawnej. Leży na terenie osiedla Partynice.");
        eventDTO2.setType("sport");
        eventDTO2.setImage("http://s9.flog.pl/media/foto/5892089_slask-wroclaw--stadion.jpg");
        eventDTO2.setPrice("82 zł");
        eventDTO2.setStartDate("27.04.2018");
        AddressDTO addressDTO6 = new AddressDTO();
        addressDTO6.setId(6);
        addressDTO6.setCity("Wrocław");
        addressDTO6.setStreet("aleja Śląska");
        addressDTO6.setHomeNumber("1");
        addressDTO6.setZipCode("54-118");
        addressDTO6.setLat("51.141568");
        addressDTO6.setLng("16.943655");
        eventDTO2.setAddress(addressDTO6);
        mRepoEvent.add(eventDTO2);

        PointDTO pointDTO = new PointDTO();
        pointDTO.setLat(addressDTO.getLat());
        pointDTO.setLng(addressDTO.getLng());
        pointDTO.setObjectId(objectDTO.getId());
        PointDTO pointDTO1 = new PointDTO();
        pointDTO1.setLat(addressDTO1.getLat());
        pointDTO1.setLng(addressDTO1.getLng());
        pointDTO1.setObjectId(objectDTO1.getId());
        PointDTO pointDTO2 = new PointDTO();
        pointDTO2.setLat(addressDTO2.getLat());
        pointDTO2.setLng(addressDTO2.getLng());
        pointDTO2.setObjectId(objectDTO2.getId());
        PointDTO pointDTO3 = new PointDTO();
        pointDTO3.setLat(addressDTO3.getLat());
        pointDTO3.setLng(addressDTO3.getLng());
        pointDTO3.setObjectId(objectDTO3.getId());
        PointDTO pointDTO4 = new PointDTO();
        pointDTO4.setLat(addressDTO4.getLat());
        pointDTO4.setLng(addressDTO4.getLng());
        pointDTO4.setObjectId(eventDTO.getId());

        List<PointDTO> list = new ArrayList<>();
        list.add(pointDTO);
        list.add(pointDTO1);
        list.add(pointDTO2);
        List<PointDTO> list1 = new ArrayList<>();
        list1.add(pointDTO3);
        list1.add(pointDTO);
        list1.add(pointDTO4);
        List<PointDTO> list2 = new ArrayList<>();
        list2.add(pointDTO2);
        list2.add(pointDTO3);
        list2.add(pointDTO4);

        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setId(0);
        routeDTO.setName("łatwa");
        routeDTO.setLength("14 min");
        routeDTO.setType("las");
        routeDTO.setPoints(list);
        RouteDTO routeDTO1 = new RouteDTO();
        routeDTO1.setId(1);
        routeDTO1.setName("trudniejsza");
        routeDTO1.setLength("20 min");
        routeDTO1.setType("woda");
        routeDTO1.setPoints(list1);
        RouteDTO routeDTO2 = new RouteDTO();
        routeDTO.setId(2);
        routeDTO2.setName("średnia");
        routeDTO2.setType("woda");
        routeDTO2.setLength("50 min");
        routeDTO2.setPoints(list2);

        mRepoRoutes.add(routeDTO);
        mRepoRoutes.add(routeDTO1);
        mRepoRoutes.add(routeDTO2);
    }
}
