package com.shafi.sbf.onlineradio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.shafi.sbf.onlineradio.adapter.RadioFrequencyAdapter;
import com.shafi.sbf.onlineradio.player.PlaybackStatus;
import com.shafi.sbf.onlineradio.player.RadioManager;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaPlayer extends AppCompatActivity {

    public static String urlPoss;

    private List<String> image = new ArrayList<>();
    private List<String> frequency = new ArrayList<>();
    private List<String> url = new ArrayList<>();

     String current_Url;


    private View lastSelectedItem;

    RadioFrequencyAdapter mAdapter;

    @BindView(R.id.playTrigger2)
    ImageButton trigger2;

//    @BindView(R.id.fm_recyclerView)
//    RecyclerView RecyclerView;

    ImageView imageView;
    RadioManager radioManager;

    public String streamURL;

    private RecyclerView fmRecyclerview;

    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);




        initControls();

        ButterKnife.bind(this);
        radioManager = RadioManager.with(this);

        imageView = findViewById(R.id.radio_logo2);

        Intent intent = getIntent();
        image = intent.getStringArrayListExtra("image");
        frequency = intent.getStringArrayListExtra("fre");
        url = intent.getStringArrayListExtra("url");


        current_Url = intent.getStringExtra("Frequency");

        Log.d("asd",""+current_Url);

        streamURL = current_Url;

        initialRecyclerView();


    }


    private void initialRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        RecyclerView recyclerView = findViewById(R.id.fm_recyclerView);

        recyclerView.setLayoutManager(layoutManager);

        RadioFrequencyAdapter radioFrequencyAdapter = new RadioFrequencyAdapter(this,frequency,url);

        recyclerView.setAdapter(radioFrequencyAdapter);

    }




    @Subscribe
    public void onEvent(String status){

        switch (status){

            case PlaybackStatus.LOADING:

                // loading

                break;

            case PlaybackStatus.ERROR:

                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();

                break;

        }

        trigger2.setImageResource(status.equals(PlaybackStatus.PLAYING)
                ? R.drawable.ic_pause_white
                : R.drawable.ic_play_arrow_black);

    }



    @OnClick(R.id.playTrigger2)
    public void onClicked(){

        if(TextUtils.isEmpty(streamURL)) return;

        radioManager.playOrPause(streamURL);
    }


    private void initControls() {
        try {
            volumeSeekbar = (SeekBar) findViewById(R.id.volume_seekbar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            int index = volumeSeekbar.getProgress();
            volumeSeekbar.setProgress(index + 1);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            int index = volumeSeekbar.getProgress();
            volumeSeekbar.setProgress(index - 1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
