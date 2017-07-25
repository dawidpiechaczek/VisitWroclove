package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.utils.Constants;
import com.google.android.gms.vision.text.Text;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 20.07.2017.
 */

public class DetailsActivity extends BaseActivity {
    Bundle extras;
    List<ObjectDTO> list1;
    List<EventDTO> list2;
    @BindView(R.id.ad_im_image)
    public ImageView image;
    @BindView(R.id.ad_tv_name)
    public TextView name;
    @BindView(R.id.ad_btn_favourite)
    public Button favourite;
    @BindView(R.id.ad_tv_description)
    public TextView description;
    @BindView(R.id.ad_tv_address)
    public TextView address;
    @Inject
    public ObjectDAOImpl mRepoObjects;
    @Inject
    public EventDAOImpl mRepoEvents;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        extras = getIntent().getExtras();
        String activityType = extras.getString(Constants.EXTRA_ACTIVITY);
        if (activityType.equals(Constants.ACTIVITY_VALUE_EVENT)) {
            list2 = mRepoEvents.getAll();
            loadEventImage();
        } else {
            list1 = mRepoObjects.getAll();
            loadObjectImage();
        }

    }

    private void loadEventImage() {
        int itemPosition = extras.getInt(Constants.EXTRA_POSIOTION);

        name.setText(list2.get(itemPosition).getName());
        description.setText(list2.get(itemPosition).getDescription());
        address.setText(list2.get(itemPosition).getAddress().getStreet() + " " + list2.get(itemPosition).getAddress().getHomeNumber()
                + ", " + list2.get(itemPosition).getAddress().getZipCode() + " " + list2.get(itemPosition).getAddress().getCity());
        String imageUrl = list2.get(itemPosition).getImage();
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(image);
    }

    private void loadObjectImage() {
        int itemPosition = extras.getInt(Constants.EXTRA_POSIOTION);

        name.setText(list1.get(itemPosition).getName());
        description.setText(list1.get(itemPosition).getDescription());
        address.setText(list1.get(itemPosition).getAddress().getStreet() + " " + list1.get(itemPosition).getAddress().getHomeNumber()
                + ", " + list1.get(itemPosition).getAddress().getZipCode() + " " + list1.get(itemPosition).getAddress().getCity());
        String imageUrl = list1.get(itemPosition).getImage();
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(image);
    }
}