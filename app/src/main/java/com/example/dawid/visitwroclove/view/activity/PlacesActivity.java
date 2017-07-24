package com.example.dawid.visitwroclove.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerAdapter;
import com.example.dawid.visitwroclove.model.ObjectDTO;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 18.07.2017.
 */

public class PlacesActivity extends BaseActivity {
    public static final String EXTRA_POSIOTION = "item_position";
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;

    @Inject
    public ObjectDAOImpl mRepo;
    @BindView(R.id.ap_rv_recycler)
    public RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);

        initPage();
    }

    private void initPage() {
        List<ObjectDTO> list = mRepo.getAll();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(PlacesActivity.this, DetailsActivity.class);
                intent.putExtra(PlacesActivity.EXTRA_POSIOTION, position);
                Pair<View, String> pair1 = Pair.create(view.findViewById(R.id.cl_im_photo), "myImage");
                Pair<View, String> pair2 = Pair.create(view.findViewById(R.id.cl_tv_name), "myTitle");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(PlacesActivity.this, pair1, pair2);
                startActivity(intent, optionsCompat.toBundle());
            }
        });
    }


}
