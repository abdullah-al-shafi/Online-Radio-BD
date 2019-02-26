package com.shafi.sbf.onlineradio.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shafi.sbf.onlineradio.R;
import com.shafi.sbf.onlineradio.comon.Comon;
import com.shafi.sbf.onlineradio.player.RadioManager;

import java.util.List;

public class RadioFrequencyAdapter extends RecyclerView.Adapter<RadioFrequencyAdapter.ViewHolder> {

    //var
    List<String> mFrequency;
    List<String> mUrl;
    Context mContext;

    RadioManager radioManager;

    int abc;

    public RadioFrequencyAdapter(Context mContext, List<String> mFrequency, List<String> url) {
        this.mUrl = url;
        this.mFrequency = mFrequency;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.url_item, viewGroup,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.Frequency.setText(mFrequency.get(i));



        viewHolder.Frequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abc = i ;
                String streamURL = mUrl.get(i);

                radioManager.playOrPause(streamURL,i);

                Toast.makeText(mContext, mFrequency.get(i), Toast.LENGTH_SHORT).show();

            }
        });

        if(i== Comon.possition){
            viewHolder.Frequency.setTextColor(Color.parseColor("#ffffff"));
        }

    }

    @Override
    public int getItemCount() {
        return mFrequency.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Frequency;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Frequency = itemView.findViewById(R.id.StationFrequency);

            radioManager = RadioManager.with(mContext);
        }
    }

}