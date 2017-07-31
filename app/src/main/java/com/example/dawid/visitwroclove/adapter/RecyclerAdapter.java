package com.example.dawid.visitwroclove.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 18.07.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ClickListener clickListener;
    private Context context;
    private List<BaseDTO> list;


    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cl_im_photo)
        public ImageView itemImage;
        @BindView(R.id.cl_tv_name)
        public TextView itemName;
        @BindView(R.id.cl_cv_card)
        public CardView itemCard;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition >= 0) {
                clickListener.onItemClick(adapterPosition, view);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getImage())
                .centerCrop()
                .into(holder.itemImage);
        holder.itemName.setText(list.get(position).getName());
    }

    public void setOnClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setData(List list){
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
    }
}
