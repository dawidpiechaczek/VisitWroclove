package com.example.dawid.visitwroclove.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.utils.Constants;
import com.example.dawid.visitwroclove.utils.FontManager;
import com.example.dawid.visitwroclove.view.activity.DetailsActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dawid on 03.08.2017.
 */

public class WindowAdapter implements GoogleMap.InfoWindowAdapter {
    @BindView(R.id.wl_tv_name)
    TextView name;
    @BindView(R.id.wl_iv_show_details)
    TextView details;
    @BindView(R.id.wl_iv_make_route)
    TextView route;
    @BindView(R.id.wl_iv_image)
    ImageView image;

    private Context context;
    private ObjectDAOImpl mRepo;
    private HashMap<Marker, Integer> hashMap;
    private Marker marker;

    public WindowAdapter(Context context, ObjectDAOImpl repo, HashMap<Marker, Integer> hashMap) {
        this.context = context;
        this.mRepo = repo;
        this.hashMap = hashMap;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        this.marker = marker;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.window_layout, null);
        ButterKnife.bind(this, view);
        int id = hashMap.get(marker);
        ObjectDTO obj = mRepo.getById(id);
        details.setTypeface(FontManager.getIcons(context));
        route.setTypeface(FontManager.getIcons(context));
        name.setText(obj.getName());
        Glide.with(context).load(obj.getImage()).centerCrop().into(image);
        return view;
    }

    @OnClick(R.id.wl_iv_show_details)
    public void showDetailsActivity(){
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constants.EXTRA_POSIOTION, hashMap.get(marker));
        intent.putExtra(Constants.EXTRA_ACTIVITY, Constants.ACTIVITY_VALUE_OBJECT);
        context.startActivity(intent);
    }
}
