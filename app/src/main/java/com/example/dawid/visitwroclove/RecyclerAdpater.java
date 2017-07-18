package com.example.dawid.visitwroclove;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.model.ObjectDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 18.07.2017.
 */

public class RecyclerAdpater extends RecyclerView.Adapter<RecyclerAdpater.ViewHolder> {
    private ObjectDAOImpl mRepo;
    private Context context;
    private List<ObjectDTO>list;
    private String[]colors = new String[]{"#7986cb", "#5c6bc0", "#3f51b5", "#03a9f4", "#00bcd4" };

    public RecyclerAdpater(Context context){
        this.context = context;
        mRepo = new ObjectDAOImpl();
        list = mRepo.getAll();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public int currentItem;
        @BindView(R.id.cl_im_photo)
        public ImageView itemImage;
        @BindView(R.id.cl_tv_name)
        public TextView itemName;
        @BindView(R.id.cl_cv_card)
        public CardView itemCard;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TATA", getAdapterPosition() + "");
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).centerCrop().into(holder.itemImage);
        holder.itemName.setText(list.get(position).getName());
     //   holder.itemCard.setCardBackgroundColor(Color.parseColor(colors[position]));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
