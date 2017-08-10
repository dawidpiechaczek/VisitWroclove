package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerRoutesAdapter;
import com.example.dawid.visitwroclove.adapter.RoutesListAdapter;
import com.example.dawid.visitwroclove.enums.Categories;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 09.08.2017.
 */

public class RoutesListActivity extends BaseActivity {
    @Inject
    public RouteDAOImpl mRepo;
    @BindView(R.id.rla_rv_recycler)
    public RecyclerView recyclerView;
    @BindView(R.id.rla_t_toolbar)
    public Toolbar toolbar;

    private List<RouteDTO> list;
    private RecyclerView.LayoutManager layoutManager;
    private RoutesListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routeslist_activity);
        ButterKnife.bind(this);
        getComponent().inject(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setAdapter();
        setToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case (android.R.id.home):
                onBackPressed();
                return true;
            default:
                return true;
        }
    }

    private void setToolbar() {
        toolbar.setTitle("Trasy");
        toolbar.setTitleTextColor(getColor(R.color.secondaryToolbar));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setAdapter(){
        int position = getIntent().getIntExtra(Constants.EXTRA_POSIOTION, 0);
        list = new ArrayList<>();
        if (position == Categories.LAS.getValue()) {
            list = mRepo.getByType("las");
        } else if (position == Categories.WODA.getValue()) {
            list = mRepo.getByType("woda");
        } else {
            list = mRepo.getAll();
        }
        adapter = new RoutesListAdapter(this);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new RecyclerRoutesAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(RoutesListActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
}
