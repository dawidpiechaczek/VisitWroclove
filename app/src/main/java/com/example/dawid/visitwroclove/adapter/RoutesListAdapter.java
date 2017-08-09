package com.example.dawid.visitwroclove.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.utils.FontManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 09.08.2017.
 */

public class RoutesListAdapter extends RecyclerView.Adapter<RoutesListAdapter.ViewHolder> {
    private Context context;
    private RecyclerRoutesAdapter.ClickListener clickListener;
    private String[] names = new String[]{"leśne", "morskie", "rowerowe"};
    private String[] amount = new String[]{"2", "5", "12"};
    private String[] times = new String[]{"40 min", "75 min", "120 min"};

    public RoutesListAdapter(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rle_tv_name)
        public TextView itemName;
        @BindView(R.id.rle_tv_amout)
        public TextView itemAmount;
        @BindView(R.id.rle_tv_time)
        public TextView itemTime;
        @BindView(R.id.rle_tv_placesImage)
        public TextView placesImage;
        @BindView(R.id.rle_tv_timeImage)
        public TextView timeImage;

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
    public RoutesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.routelist_element, parent, false);
        return new RoutesListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemName.setText(names[position]);
        holder.itemAmount.setText(amount[position]);
        holder.itemTime.setText(times[position]);
        holder.placesImage.setTypeface(FontManager.getIcons(context));
        holder.timeImage.setTypeface(FontManager.getIcons(context));
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