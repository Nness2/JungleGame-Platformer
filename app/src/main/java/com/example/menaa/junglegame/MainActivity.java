package com.example.menaa.junglegame;

import android.content.Context;
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
    private MediaPlayer mySong;
    private String FILENAME = "memo";
    private LinkedList<String> data = new LinkedList<String>();


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
        btScore.setOnClickListener(btnScore);
        btCredit.setOnClickListener(btnCredit);
        btPlay.setOnClickListener(btnGame);
        btLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        //initFile();
        btMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicStat++;
                if (musicStat%2 == 1){
                    musicStat = 1;
                    btMusic.setBackgroundResource(R.drawable.sound);
                }
                if (musicStat%2 == 0){
                    musicStat = 0;
                    btMusic.setBackgroundResource(R.drawable.sound_off);
                }
            }
        });
    }
    private void init () {
    }

    private View.OnClickListener btnCredit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent creditActivity = new Intent(MainActivity.this, credit.class);
            startActivity(creditActivity);
        }
    };

    private View.OnClickListener btnScore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scoreActivity = new Intent(MainActivity.this, score.class);
            startActivity(scoreActivity);
        }
    };

    private View.OnClickListener btnGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gameActivity = new Intent(MainActivity.this, game.class);
            startActivity(gameActivity);
        }
    };



    private void initFile(){
        for (int i = 0 ; i < 5 ; i++){
            data.add(new String(0+"\n"));
        }
        saveData();
    }

    public void saveData() {
        FileOutputStream fos;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (String str : data) {
                fos.write(str.getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}