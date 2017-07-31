package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 20.07.2017.
 */

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.ad_im_image)
    public ImageView image;
    @BindView(R.id.ad_tv_name)
    public TextView name;
    @BindView(R.id.ad_btn_favourite)
    public FloatingActionButton favourite;
    @BindView(R.id.ad_tv_description)
    public TextView description;
    @BindView(R.id.ad_tv_address)
    public TextView address;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @Inject
    public ObjectDAOImpl mRepoObjects;
    @Inject
    public EventDAOImpl mRepoEvents;

    private Bundle extras;
    private List<BaseDTO> list;
    private int itemPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        getExtras();
    }

    private void getExtras() {
        extras = getIntent().getExtras();
        itemPosition = extras.getInt(Constants.EXTRA_POSIOTION);
        String activityType = extras.getString(Constants.EXTRA_ACTIVITY);

        if (activityType.equals(Constants.ACTIVITY_VALUE_EVENT)) {
            setList(mRepoEvents.getAll());
        } else {
            setList(mRepoObjects.getAll());
        }
        loadObjectImage();
    }

    private void setList(List list) {
        this.list = list;
    }

    private void loadObjectImage() {
        setToolbarTitle(list.get(itemPosition).getName());
        name.setText(list.get(itemPosition).getName());
        description.setText(list.get(itemPosition).getDescription());
        address.setText(list.get(itemPosition).getAddress().getStreet() + " " + list.get(itemPosition).getAddress().getHomeNumber()
                + ", " + list.get(itemPosition).getAddress().getZipCode() + " " + list.get(itemPosition).getAddress().getCity());
        String imageUrl = list.get(itemPosition).getImage();
        setImage(imageUrl);
    }

    private void setImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(image);
    }

    private void setToolbarTitle(String name) {
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}