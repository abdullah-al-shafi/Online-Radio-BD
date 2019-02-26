package com.shafi.sbf.onlineradio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shafi.sbf.onlineradio.adapter.RecylaerViewAdapter;
import com.shafi.sbf.onlineradio.player.PlaybackStatus;
import com.shafi.sbf.onlineradio.player.RadioManager;
import com.shafi.sbf.onlineradio.retrofit.Item;
import com.shafi.sbf.onlineradio.retrofit.RadioAll;
import com.shafi.sbf.onlineradio.retrofit.RadioApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    int pos;

    RecylaerViewAdapter recylaerViewAdapter;

    List<Item> items = new ArrayList<>();
    List<Item> frequency = new ArrayList<>();
    List<Item> url = new ArrayList<>();


    List<String> chanelName = new ArrayList<>();
    List<String> chanelFrequency = new ArrayList<>();
    List<String> chanelUrl = new ArrayList<>();
    List<String> chanelImage = new ArrayList<>();
    List<String> makeStringItem = new ArrayList<>();


    RadioApi radioApi;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.playTrigger)
    ImageButton trigger;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.name)
    TextView textView;

    @BindView(R.id.sub_player)
    View subPlayer;

    RadioManager radioManager;

    String streamURL,Frequency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        radioManager = RadioManager.with(this);

        networkLibraryInitialazer();

        getData();

        subPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MediaPlayer.class);

                intent.putStringArrayListExtra("url", (ArrayList<String>) chanelUrl);
                intent.putStringArrayListExtra("fre", (ArrayList<String>) chanelFrequency);
                intent.putStringArrayListExtra("image", (ArrayList<String>) chanelImage);

                intent.putExtra("Frequency",Frequency);

                if(!Frequency.isEmpty()){
                    startActivity(intent);
                }
            }
        });

        //    listView.setAdapter(new ShoutcastListAdapter(this, ShoutcastHelper.retrieveShoutcasts(this)));
    }

    private void networkLibraryInitialazer() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://radioapi.bdixapps.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        radioApi = retrofit.create(RadioApi.class);
    }

    private void getData() {

        Call<RadioAll> allListCall = radioApi.getAllData();
        allListCall.enqueue(new Callback<RadioAll>() {
            @Override
            public void onResponse(Call<RadioAll> call, Response<RadioAll> response) {

                items.addAll(response.body().getItems());


                makeStringItem(items);

            }

            @Override
            public void onFailure(Call<RadioAll> call, Throwable t) {

            }
        });

    }

    private void makeStringItem(List<Item> items) {

        for (int i = 0; i < items.size(); i++) {
            makeStringItem.add(items.get(i).getName());
            chanelName.add(items.get(i).getName());
            chanelFrequency.add(items.get(i).getApk());
            chanelUrl.add(items.get(i).getUrl());
            chanelImage.add(items.get(i).getUrl());
        }

        recylaerViewAdapter = new RecylaerViewAdapter(items);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recylaerViewAdapter);

        //oneClickListener.......


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

                pos = position;

                textView.setText(chanelName.get(position));

                subPlayer.setVisibility(View.VISIBLE);

                streamURL = chanelUrl.get(position);

                radioManager.playOrPause(streamURL,position);

                Frequency = chanelFrequency.get(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    public void next(View view) {
        if(pos==items.size()-1){
            pos=0;

            textView.setText(chanelName.get(pos));

            subPlayer.setVisibility(View.VISIBLE);

            streamURL = chanelUrl.get(pos);

            radioManager.playOrPause(streamURL);

        }else {
            pos = pos +1;

            textView.setText(chanelName.get(pos));

            subPlayer.setVisibility(View.VISIBLE);

            streamURL = chanelUrl.get(pos);

            radioManager.playOrPause(streamURL);


        }

        pos = pos;

    }

    public void previous(View view) {
        if(pos==0){
            pos = items.size()-1;

            textView.setText(chanelName.get(pos));

            subPlayer.setVisibility(View.VISIBLE);

            streamURL = chanelUrl.get(pos);

            radioManager.playOrPause(streamURL);

        }else {
            pos = pos - 1;

            textView.setText(chanelName.get(pos));

            subPlayer.setVisibility(View.VISIBLE);

            streamURL = chanelUrl.get(pos);

            radioManager.playOrPause(streamURL);


        }

        pos = pos;

    }


    @Override
    public void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        radioManager.unbind();

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        radioManager.bind();
    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Subscribe
    public void onEvent(String status) {

        switch (status) {

            case PlaybackStatus.LOADING:

                // loading

                break;

            case PlaybackStatus.ERROR:

                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();

                break;

        }

        trigger.setImageResource(status.equals(PlaybackStatus.PLAYING)
                ? R.drawable.ic_pause_black
                : R.drawable.ic_play_arrow_black);

    }

    @OnClick(R.id.playTrigger)
    public void onClicked() {

        if (TextUtils.isEmpty(streamURL)) return;

        radioManager.playOrPause(streamURL);
    }




//    @OnItemClick(R.id.listview)
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//
//        Shoutcast shoutcast = (Shoutcast) parent.getItemAtPosition(position);
//        if(shoutcast == null){
//
//            return;
//
//        }
//
//        textView.setText(shoutcast.getName());
//
//        subPlayer.setVisibility(View.VISIBLE);
//
//        streamURL = shoutcast.getUrl();
//
//        radioManager.playOrPause(streamURL);
//    }

    ///for clickListener..........
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
