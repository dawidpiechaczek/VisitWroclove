package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerRoutesAdapter;
import com.example.dawid.visitwroclove.adapter.RoutesListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 09.08.2017.
 */

public class RoutesListActivity extends BaseActivity{

    @BindView(R.id.rla_rv_recycler)
    public RecyclerView recyclerView;
    @BindView(R.id.rla_t_toolbar)
    public Toolbar toolbar;

    private RecyclerView.LayoutManager layoutManager;
    private RoutesListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routeslist_activity);
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RoutesListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new RecyclerRoutesAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(RoutesListActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        setToolbar();
    }

    private void setToolbar() {
        toolbar.setTitle("Trasy");
        toolbar.setTitleTextColor(getColor(R.color.secondaryToolbar));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
