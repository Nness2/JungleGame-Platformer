package com.example.menaa.junglegame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private Button btScore;
    private Button btCredit;
    private Button btLeave;
    private Button btMusic;
    private Button btPlay;
    private int musicStat = 1;
    private MediaPlayer mySong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //image = (ImageView) findViewById(R.id.imageView1);
        btScore = (Button) findViewById(R.id.btScore);
        btCredit = (Button) findViewById(R.id.btCredit);
        btLeave = (Button) findViewById(R.id.btLeave);
        btMusic = (Button) findViewById(R.id.btMusic);
        btPlay = (Button) findViewById(R.id.btPlay);
        mySong = MediaPlayer.create(MainActivity.this,R.raw.platformer);
        btScore.setOnClickListener(btnTestListener1);
        btCredit.setOnClickListener(btnCredit);
        btPlay.setOnClickListener(btnGame);
        btLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySong.stop();
                finish();
            }
        });
        btMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicStat++;
                if (musicStat%2 == 1){
                    musicStat = 1;
                    mySong.start();
                    btMusic.setBackgroundResource(R.drawable.sound);
                }
                if (musicStat%2 == 0){
                    musicStat = 0;
                    mySong.pause();
                    btMusic.setBackgroundResource(R.drawable.sound_off);
                }
            }
        });
        init();
    }
    private void init () {
        mySong.start();
    }

    private View.OnClickListener btnCredit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent creditActivity = new Intent(MainActivity.this, credit.class);
            startActivity(creditActivity);
        }
    };

    private View.OnClickListener btnGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mySong.stop();
            Intent gameActivity = new Intent(MainActivity.this, game.class);
            startActivity(gameActivity);
        }
    };

    private View.OnClickListener btnTestListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("DEBUG", "CLIQUttE BUTTON");
        }
    };


}