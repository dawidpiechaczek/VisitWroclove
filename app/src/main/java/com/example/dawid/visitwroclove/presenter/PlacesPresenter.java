package com.example.dawid.visitwroclove.presenter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.adapter.RecyclerAdapter;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.view.interfaces.PlacesView;

import java.util.List;

/**
 * Created by DPIECHAC on 2017-09-11.
 */

public class PlacesPresenter extends BasePresenter<PlacesView> {
    private ObjectDAOImpl mRepoObjects;
    private List<ObjectDTO> list;
    private RecyclerAdapter adapter;
    private Context context;
    private RecyclerView recyclerView;

    public PlacesPresenter(ObjectDAOImpl mRepoObjects) {
        this.mRepoObjects = mRepoObjects;
    }

    public void sort(int itemId) {
        if (itemId == R.id.visit) {
            list = mRepoObjects.getByType("building");
        } else if (itemId == R.id.eat) {
            list = mRepoObjects.getByType("visit");
        } else {
            list = mRepoObjects.getAll();
        }
        sortWithCategory(list);
    }

    private void sortWithCategory(List<ObjectDTO> list) {
        adapter.setData(list);
        this.list = list;
        adapter.notifyDataSetChanged();
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void iniziallizeAllView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(context);
        list = mRepoObjects.getAll();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                getView().onClickOnAdapter(list.get(position).getId(),view);
            }
        });
    }
}
