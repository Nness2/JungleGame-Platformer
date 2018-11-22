package com.example.menaa.junglegame;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private Button btScore;
    private Button btCredit;
    private Button btLeave;
    private Button btMusic;
    private Button btPlay;
    private int musicStat = 1;
    private int mStop = 0;
    private MediaPlayer mySong;
    private String FILENAME = "memo";
    private LinkedList<String> data = new LinkedList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySong = MediaPlayer.create(MainActivity.this,R.raw.platformer);
        btScore = (Button) findViewById(R.id.btScore);
        btCredit = (Button) findViewById(R.id.btCredit);
        btLeave = (Button) findViewById(R.id.btLeave);
        btMusic = (Button) findViewById(R.id.btMusic);
        btPlay = (Button) findViewById(R.id.btPlay);
        btScore.setOnClickListener(btnScore);
        btCredit.setOnClickListener(btnCredit);
        btPlay.setOnClickListener(btnGame);
        btLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onPause();
            }
        });

        btMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicStat++;
                if (musicStat%2 == 1){
                    musicStat = 1;
                    btMusic.setBackgroundResource(R.drawable.sound);
                    mySong.start();
                }
                if (musicStat%2 == 0){
                    musicStat = 0;
                    btMusic.setBackgroundResource(R.drawable.sound_off);
                    mySong.pause();
                }
            }
        });

        init();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    private void init () {
    }

    private View.OnClickListener btnCredit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mStop = 1;
            Intent creditActivity = new Intent(MainActivity.this, credit.class);
            startActivity(creditActivity);
        }
    };

    private View.OnClickListener btnScore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mStop = 1;
            Intent scoreActivity = new Intent(MainActivity.this, score.class);
            startActivity(scoreActivity);
        }
    };

    private View.OnClickListener btnGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mStop = 1;
            Intent gameActivity = new Intent(MainActivity.this, game.class);
            startActivity(gameActivity);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mStop == 0)
            mySong.pause();
        mStop = 0;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (musicStat%2 == 1)
            mySong.start();
    }

}