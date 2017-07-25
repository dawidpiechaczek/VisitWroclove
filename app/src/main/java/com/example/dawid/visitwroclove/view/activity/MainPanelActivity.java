package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;


import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.AddressDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainPanelActivity extends BaseActivity {

    private String mLog = MainPanelActivity.class.getName();
    @BindView(R.id.ll_map)
    public LinearLayout mpa_ll_map;
    @BindView(R.id.ll_events)
    public LinearLayout mpa_ll_events;
    @BindView(R.id.ll_places)
    public LinearLayout mpa_ll_places;
    @BindView(R.id.ll_tracks)
    public LinearLayout mpa_ll_tracks;
    @Inject
    public ObjectDAOImpl mRepo;
    @Inject
    public EventDAOImpl mRepoEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_panel);
        ButterKnife.bind(this);
        Log.d(mLog, "MainPanelActivity.onCreate()");
        scripts();

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
        objectDTO.setDescription("Budynek gdzie mieści się starostwo miasta Wrocław.");
        objectDTO.setPhone("42 455 34 43");
        objectDTO.setRank(7.53);
        objectDTO.setType("building");
        objectDTO.setImage("https://upload.wikimedia.org/wikipedia/commons/5/52/2017_Ratusz_Staromiejski_we_Wroc%C5%82awiu_01.jpg");
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(0);
        addressDTO.setCity("Wrocław");
        addressDTO.setStreet("Rynek");
        addressDTO.setHomeNumber("19");
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
        addressDTO1.setZipCode("50-200");
        addressDTO1.setStreet("Biskupin");
        addressDTO1.setHomeNumber("14");
        addressDTO1.setLat("51.106586");
        addressDTO1.setLng("17.077028");
        objectDTO1.setAddress(addressDTO1);
        mRepo.add(objectDTO1);

        ObjectDTO objectDTO2 = new ObjectDTO();
        objectDTO2.setId(2);
        objectDTO2.setFavourite(false);
        objectDTO2.setName("ZOO");
        objectDTO2.setDescription("Odbywają się w niej liczbne wydarzenia kulturalne.");
        objectDTO2.setPhone("42 455 34 43");
        objectDTO2.setRank(9.53);
        objectDTO2.setType("building");
        objectDTO2.setImage("http://www.zoo.wroclaw.pl/zdc/wp-content/uploads/2016/09/logo-ZOO-WROCLAW.jpg");
        AddressDTO addressDTO2 = new AddressDTO();
        addressDTO2.setId(2);
        addressDTO2.setCity("Wrocław");
        addressDTO2.setStreet("Biskupin");
        addressDTO2.setHomeNumber("14");
        addressDTO2.setLat("51.106586");
        addressDTO2.setLng("17.077028");
        objectDTO2.setAddress(addressDTO2);
        mRepo.add(objectDTO2);

        ObjectDTO objectDTO3 = new ObjectDTO();
        objectDTO3.setId(3);
        objectDTO3.setFavourite(false);
        objectDTO3.setName("Galeria Dominikańska");
        objectDTO3.setDescription("Odbywają się w niej liczbne wydarzenia kulturalne.");
        objectDTO3.setPhone("42 455 34 43");
        objectDTO3.setRank(9.53);
        objectDTO3.setType("building");
        objectDTO3.setImage("http://galeria-dominikanska.pl/files/gallery/fotom-2196.jpg");
        AddressDTO addressDTO3 = new AddressDTO();
        addressDTO3.setId(3);
        addressDTO3.setCity("Wrocław");
        addressDTO3.setStreet("Biskupin");
        addressDTO3.setHomeNumber("14");
        addressDTO3.setLat("51.106586");
        addressDTO3.setLng("17.077028");
        objectDTO3.setAddress(addressDTO3);
        mRepo.add(objectDTO3);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(0);
        eventDTO.setFavourite(true);
        eventDTO.setName("Wyścigi konne");
        eventDTO.setDescription("Budynek gdzie mieści się starostwo miasta Wrocław.");
        eventDTO.setType("event");
        eventDTO.setImage("http://www.iceis.pl/wyscigi/konne/wyscigi-konne_partynice,400px.jpg");
        AddressDTO addressDTO4 = new AddressDTO();
        addressDTO4.setId(0);
        addressDTO4.setCity("Wrocław");
        addressDTO4.setStreet("Rynek");
        addressDTO4.setHomeNumber("19");
        addressDTO4.setLat("51.109678");
        addressDTO4.setLng("17.031879");
        eventDTO.setAddress(addressDTO4);
        mRepoEvent.add(eventDTO);
    }
}
