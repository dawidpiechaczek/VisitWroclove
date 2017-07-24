package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 20.07.2017.
 */

public class DetailsActivity extends BaseActivity{
    private List<ObjectDTO> list;

    @BindView(R.id.ad_im_image)
    public ImageView image;
    @BindView(R.id.ad_tv_name)
    public TextView name;
    @Inject
    public ObjectDAOImpl mRepo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        list = mRepo.getAll();
        loadImage();
    }

    private void loadImage() {
        Bundle extras = getIntent().getExtras();
        int itemPosition = extras.getInt(PlacesActivity.EXTRA_POSIOTION);

        name.setText(list.get(itemPosition).getName());
        String imageUrl = list.get(itemPosition).getImage();
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(image);
    }
}