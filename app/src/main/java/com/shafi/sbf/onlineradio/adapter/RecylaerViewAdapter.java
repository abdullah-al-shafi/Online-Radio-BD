package com.shafi.sbf.onlineradio.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shafi.sbf.onlineradio.R;
import com.shafi.sbf.onlineradio.retrofit.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecylaerViewAdapter extends RecyclerView.Adapter<RecylaerViewAdapter.MyViewHolder> {

    private List<Item> items;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView StationName, StationFreq;
        public ImageView RadioImage;



        public MyViewHolder(View view) {
            super(view);
            StationName = (TextView) view.findViewById(R.id.StationName);
            StationFreq = (TextView) view.findViewById(R.id.StationFrequency);
            RadioImage = (ImageView) view.findViewById(R.id.radioImage);

        }
    }


    public RecylaerViewAdapter(List<Item> items) {
        this.items = items;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = items.get(position);
        holder.StationName.setText(item.getName());
        holder.StationFreq.setText(item.getApk());
        Picasso.get().load(item.getAltUrl()).into(holder.RadioImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}