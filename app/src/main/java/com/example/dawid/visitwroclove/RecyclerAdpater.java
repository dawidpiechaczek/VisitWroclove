package com.example.dawid.visitwroclove;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        Glide.with(context).load(list.get(position).getImage()).into(holder.itemImage);
        holder.itemName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
