package com.example.dawid.visitwroclove.view.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerAdapter;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 18.07.2017.
 */

public class PlacesActivity extends BaseActivity {
    @Inject
    public ObjectDAOImpl mRepo;
    @BindView(R.id.ap_rv_recycler)
    public RecyclerView recyclerView;
    @BindView(R.id.toolbar2)
    public Toolbar toolbar;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<ObjectDTO> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        initPage();
        setToolbar();
    }

    private void setToolbar() {
        toolbar.setTitle("Obiekty");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case (android.R.id.home):
                onBackPressed();
                return true;
            case (R.id.visit):
                sortWithCategory("building");
                return true;
            case (R.id.eat):
                sortWithCategory("visit");
                return true;
            default:
                sortWithCategory("all");
                return true;
        }
    }

    private void sortWithCategory(String category) {
        List<ObjectDTO> list;
        if (category.equals("all")) {
            list = mRepo.getAll();
        } else {
            list = mRepo.getByType(category);
        }
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    private void initPage() {
        list = mRepo.getAll();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(this);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(PlacesActivity.this, DetailsActivity.class);
                intent.putExtra(Constants.EXTRA_POSIOTION, list.get(position).getId());
                intent.putExtra(Constants.EXTRA_ACTIVITY, Constants.ACTIVITY_VALUE_OBJECT);
                Pair<View, String> pair1 = Pair.create(view.findViewById(R.id.cl_im_photo), Constants.TRANSITION_IMAGE);
                Pair<View, String> pair2 = Pair.create(view.findViewById(R.id.cl_tv_name), Constants.TRANSITION_NAME);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(PlacesActivity.this, pair1, pair2);
                startActivity(intent, optionsCompat.toBundle());
            }
        });
    }


}
