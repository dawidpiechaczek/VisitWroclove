package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.RecyclerAdpater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 18.07.2017.
 */

public class PlacesActivity extends AppCompatActivity {

    @BindView(R.id.ap_rv_recycler)
    public RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdpater(this);
        recyclerView.setAdapter(adapter);
    }

}
