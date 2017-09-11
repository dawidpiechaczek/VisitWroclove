package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dawid on 20.07.2017.
 */

public class DetailsActivity extends BaseActivity {
    private Bundle extras;
    private BaseDTO list;
    private int itemId;
    private String activityType;
    private boolean isFavourite = false;

    @BindView(R.id.ad_im_image) public ImageView image;
    @BindView(R.id.ad_btn_favourite) public FloatingActionButton favourite;
    @BindView(R.id.ad_tv_description) public TextView description;
    @BindView(R.id.ad_tv_address) public TextView address;
    @BindView(R.id.toolbar) public Toolbar toolbar;
    @BindView(R.id.ad_ll_event_details) public LinearLayout linearLayout;
    @BindView(R.id.ad_tv_prize) public TextView prize;
    @BindView(R.id.ad_tv_date) public TextView date;
    @Inject public ObjectDAOImpl mRepoObjects;
    @Inject public EventDAOImpl mRepoEvents;

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
        itemId = extras.getInt(Constants.EXTRA_POSIOTION);
        activityType = extras.getString(Constants.EXTRA_ACTIVITY);

        if (activityType.equals(Constants.ACTIVITY_VALUE_EVENT)) {
            setObject(mRepoEvents.getById(itemId));
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            setObject(mRepoObjects.getById(itemId));
            linearLayout.setVisibility(View.GONE);
        }
        loadObject();
    }

    private void setObject(BaseDTO dto) {
        this.list = dto;
    }

    private void loadObject() {
        setToolbarTitle(list.getName());
        description.setText(list.getDescription());
        address.setText(list.getAddress().getStreet() + " " + list.getAddress().getHomeNumber()
                + ", " + list.getAddress().getZipCode() + " " + list.getAddress().getCity());
        if (activityType.equals(Constants.ACTIVITY_VALUE_EVENT)) {
            date.setText("Data: " + ((EventDTO) list).getStartDate());
            prize.setText("Cena: " + ((EventDTO) list).getPrice());
        }

        String imageUrl = list.getImage();
        setImage(imageUrl);
        if (list.isFavourite()) {
            favourite.setImageResource(R.drawable.ic_heart_clicked);
            isFavourite = true;
        } else {
            favourite.setImageResource(R.drawable.ic_action_name);
            isFavourite = false;
        }
    }

    private void setImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .dontAnimate()
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

    @OnClick(R.id.ad_btn_favourite)
    public void setFavourite() {
        if (isFavourite) {
            favourite.setImageResource(R.drawable.ic_action_name);
            isFavourite = !isFavourite;
        } else {
            favourite.setImageResource(R.drawable.ic_heart_clicked);
            isFavourite = !isFavourite;
        }
    }
}