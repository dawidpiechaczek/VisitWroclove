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
import com.example.dawid.visitwroclove.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 09.08.2017.
 */

public class RecyclerRoutesAdapter extends RecyclerView.Adapter<RecyclerRoutesAdapter.ViewHolder> {
    private Context context;
    private ClickListener clickListener;
    private String[] photos = new String[]{"http://newsroom.unfccc.int/media/777629/flickr_projoshua-mayer_cpforets.jpg?crop=0,0.39939691904293673,0,0.097161586365126187&cropmode=percentage&width=768&height=256&rnd=131234398060000000", "https://static1.squarespace.com/static/576da6e3414fb5ee34116cd4/t/5785816a20099e1db588a530/1467950897417/photo-1441829266145-6d4bfbd38eb4.jpeg?format=1500w", "https://magazynbike.pl/wp-content/uploads/2017/07/Fot-0003-1-411x297.jpg"};
    private String[] names = new String[]{"leśne", "morskie", "rowerowe"};

    public RecyclerRoutesAdapter(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cr_im_photo)
        public ImageView itemImage;
        @BindView(R.id.cr_tv_name)
        public TextView itemName;
        @BindView(R.id.cr_cv_card)
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
    public RecyclerRoutesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_routes, parent, false);
        return new RecyclerRoutesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(photos[position])
                .centerCrop()
                .into(holder.itemImage);
        holder.itemName.setText(names[position]);
    }

    public void setOnClickListener(RecyclerRoutesAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
    }
}
