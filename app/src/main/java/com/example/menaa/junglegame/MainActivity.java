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

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private MediaPlayer mySong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //image = (ImageView) findViewById(R.id.imageView1);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        mySong = MediaPlayer.create(MainActivity.this,R.raw.platformer);
        btn1.setOnClickListener(btnTestListener1);
        btn2.setOnClickListener(btnCredit);
        btn5.setOnClickListener(btnGame);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        mySong.start();
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

    private View.OnClickListener btnGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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