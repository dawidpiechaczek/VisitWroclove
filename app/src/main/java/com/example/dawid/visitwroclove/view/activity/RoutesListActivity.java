package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
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
    @Inject public RouteDAOImpl mRepo;
    @BindView(R.id.rla_rv_recycler) public RecyclerView recyclerView;
    @BindView(R.id.rla_t_toolbar) public Toolbar toolbar;

    private List<RouteDTO> list;
    private RecyclerView.LayoutManager layoutManager;
    private RoutesListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routeslist_activity);
        ButterKnife.bind(this);
        getComponent().inject(this);
        setAdapter();
        setToolbar();
    }

    private void setToolbar() {
        toolbar.setTitle(getString(R.string.routes));
        toolbar.setTitleTextColor(getColor(R.color.secondaryToolbar));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setAdapter(){
        String type = getIntent().getStringExtra(Constants.EXTRA_POSIOTION);
        list = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (Categories.FOREST.getValue().equals(type)) {
            list = mRepo.getByType(Categories.FOREST.getValue());
        } else if (Categories.WATER.getValue().equals(type)) {
            list = mRepo.getByType(Categories.WATER.getValue());
        } else if (Categories.CYCLE.getValue().equals(type)) {
            list = mRepo.getByType(Categories.CYCLE.getValue());
        } else if (Categories.COOK.getValue().equals(type)) {
            list = mRepo.getByType(Categories.COOK.getValue());
        } else if (Categories.FAVOURITE.getValue().equals(type)) {
            list = mRepo.getByType(Categories.FAVOURITE.getValue());
        } else if (Categories.WALKING.getValue().equals(type)) {
            list = mRepo.getByType(Categories.WALKING.getValue());
        }else {
            list = mRepo.getAll();
        }

        adapter = new RoutesListAdapter(this);
        adapter.setData(list);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new RoutesListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(RoutesListActivity.this, MapActivity.class);
                intent.putExtra("trasa", list.get(position).getId());
                intent.putExtra("own_route_mode", false); //not run own route creator mode
                startActivity(intent);
            }
        });
    }
}
