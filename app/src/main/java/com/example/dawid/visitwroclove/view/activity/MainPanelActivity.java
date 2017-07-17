package com.example.dawid.visitwroclove.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;


import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.AddressDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainPanelActivity extends Activity {

    private String mLog = MainPanelActivity.class.getName();
    @BindView(R.id.ll_map)
    public LinearLayout mpa_ll_map;
    private ObjectDAOImpl mRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        ButterKnife.bind(this);
        Log.d(mLog, "MainPanelActivity.onCreate()");

        mRepo = new ObjectDAOImpl();
        ObjectDTO objectDTO = new ObjectDTO();
        objectDTO.setDescription("lala");
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity("Wodzislaw");
        addressDTO.setStreet("cze");
        objectDTO.setAddress(addressDTO);
        mRepo.add(objectDTO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(mLog, "MainPanelActivity.onCResume()");
    }

    @OnClick(R.id.ll_map)
    public void showMapActivity() {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);
    }
}
