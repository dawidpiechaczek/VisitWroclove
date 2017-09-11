package com.example.dawid.visitwroclove.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.presenter.PlacesPresenter;
import com.example.dawid.visitwroclove.utils.Constants;
import com.example.dawid.visitwroclove.view.interfaces.PlacesView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 18.07.2017.
 */

public class PlacesActivity extends BaseActivity implements PlacesView {
    @BindView(R.id.ap_rv_recycler) RecyclerView recyclerView;
    @BindView(R.id.ap_t_toolbar) Toolbar toolbar;
    @Inject ObjectDAOImpl mRepo;

    private PlacesPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void onResume() {
        presenter.attachView(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        presenter.detachView();
        super.onPause();
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

        if (itemId == android.R.id.home) {
            onBackPressed();
        } else if (itemId == R.id.file) {
            //do nothing
        } else {
            presenter.sort(itemId);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickOnAdapter(int id, View view) {
        Intent intent = new Intent(PlacesActivity.this, DetailsActivity.class);
        intent.putExtra(Constants.EXTRA_POSIOTION, id);
        intent.putExtra(Constants.EXTRA_ACTIVITY, Constants.ACTIVITY_VALUE_OBJECT);
        Pair<View, String> pair1 = Pair.create(view.findViewById(R.id.cl_im_photo), Constants.TRANSITION_IMAGE);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(PlacesActivity.this, pair1);
        startActivity(intent, optionsCompat.toBundle());
    }

    private void setToolbar() {
        toolbar.setTitle("Miejsca");
        toolbar.setTitleTextColor(getColor(R.color.secondaryToolbar));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void init() {
        presenter = new PlacesPresenter(mRepo);
        presenter.setContext(PlacesActivity.this);
        presenter.setRecyclerView(recyclerView);
        presenter.iniziallizeAllView();
        setToolbar();
    }
}
