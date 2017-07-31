package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerAdapter;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 24.07.2017.
 */

public class EventsActivity extends BaseActivity{
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<EventDTO> list;

    @Inject
    public EventDAOImpl mRepo;
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
        list = mRepo.getAll();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(list);
        adapter.setOnClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(EventsActivity.this, DetailsActivity.class);
                intent.putExtra(Constants.EXTRA_POSIOTION, list.get(position).getId());
                intent.putExtra(Constants.EXTRA_ACTIVITY, Constants.ACTIVITY_VALUE_EVENT);
                Pair<View, String> pair1 = Pair.create(view.findViewById(R.id.cl_im_photo), Constants.TRANSITION_IMAGE);
                Pair<View, String> pair2 = Pair.create(view.findViewById(R.id.cl_tv_name), Constants.TRANSITION_NAME);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EventsActivity.this, pair1, pair2);
                startActivity(intent, optionsCompat.toBundle());
            }
        });
    }
}
