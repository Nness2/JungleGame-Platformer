package com.example.menaa.junglegame;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.util.Timer;
import java.util.TimerTask;

public class game extends AppCompatActivity {
    AnimationDrawable run;
    private ImageView img;

    private int screenWidth;
    private int screenHeight;

    private  int cmp = 0;
    private  float manJSpeed = 10;
    private  int obSpeed = 5;
    private int flag;
    private TextView score;
    private int scr;
    private Button jump;
    private Button brk;
    private ImageView map;
    private ImageView map2;
    private ImageView obstacle;
    private ImageView bonus;
    private ImageView man;
    private ImageView floor;
    private ImageView floor2;
    private int base;
    private int state = 1;

    private  float mapLeftX;
    private  float mapLeftY;
    private float map2LeftX;
    private float map2LeftY;
    private float obstacleLeftX;
    private float obstacleLeftY;
    private float bonusLeftX;
    private float floorLeftX;
    private float floor2LeftX;

    private float manLeftX;
    private float manLeftY;

    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private Timer timer2 = new Timer();
    private Timer timer3 = new Timer();
    private Timer timer4 = new Timer();
    private Rect rc1 = new Rect();
    private Rect rc2 = new Rect();
    private Rect rc3 = new Rect();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        map = (ImageView) findViewById(R.id.map);
        score = (TextView) findViewById(R.id.score);
        map2 = (ImageView) findViewById(R.id.map2);
        jump = (Button) findViewById(R.id.jump);
        brk = (Button) findViewById(R.id.brk);
        obstacle = (ImageView) findViewById(R.id.obstacle);
        bonus = (ImageView) findViewById(R.id.bonus);
        man = (ImageView) findViewById(R.id.man);
        floor = (ImageView) findViewById(R.id.floor);
        floor2 = (ImageView) findViewById(R.id.floor2);
        //map2.setX(-80.0f);
        //map2.setY(-80.0f);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(man.getY() == base){
                    flag = 0;
                    man.setBackgroundResource(R.drawable.r2);
                }


                else {
                    flag = 3;
                    man.setBackgroundResource(R.drawable.r10);
                }

            }
        });

        brk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (state == 1) {
                    brk.setBackgroundResource(R.drawable.play);
                    state = 0;
                    if (flag == 2)
                        man.setBackgroundResource(R.drawable.r3);
                }
                else{
                    brk.setBackgroundResource(R.drawable.pause);
                    state = 1;
                    if (flag == 2) {
                        man.setBackgroundResource(R.drawable.animation);
                        run = (AnimationDrawable) man.getBackground();
                        run.start();
                    }
                }
            }
        });

        man = (ImageView) findViewById(R.id.man);
        man.setBackgroundResource(R.drawable.animation);
        run = (AnimationDrawable) man.getBackground();
        run.start();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (state == 1) {
                            mvBackGround();
                            mvObstacle();
                            mvJump();
                            colision();
                        }
                    }
                });
            }
        }, 0, 10);
        init();
    }



    public void mvBackGround(){
        mapLeftX -= 1;
        if (map.getX() + map.getWidth() < 0){
            mapLeftX = screenWidth-15;
        }

        map2LeftX -= 1;
        if (map2.getX() + map2.getWidth() < 0){
            map2LeftX = screenWidth-15;
        }
        map.setX(mapLeftX);
        map.setY(mapLeftY);
        map2.setX(map2LeftX);
        map2.setY(map2LeftY);

    }

    public void mvObstacle() {
        scr++;
        score.setText("SCORE : " + Integer.toString(scr));
        if (scr % 1000 == 0) {
            obSpeed += 1;
        }
        obstacleLeftX -= obSpeed;
        bonusLeftX -= obSpeed;
        floorLeftX -= obSpeed;
        floor2LeftX -= obSpeed;
        if (obstacle.getX() + obstacle.getWidth() < 0)
            obstacleLeftX = screenWidth;
        if (bonus.getX() + bonus.getWidth() < 0){
            bonusLeftX = screenWidth + screenWidth /2;
            bonus.setVisibility(View.VISIBLE);
        }
        if (floor.getX() + floor.getWidth() < 0)
            floorLeftX = screenWidth-15;
        if (floor2.getX() + floor2.getWidth() < 0)
            floor2LeftX = screenWidth-15;

        obstacle.setX(obstacleLeftX);
        obstacle.setY(obstacleLeftY);
        floor.setX(floorLeftX);
        floor2.setX(floor2LeftX);
        bonus.setX(bonusLeftX);
    }

    public void mvJump(){

        if (flag == 0){
            if (manJSpeed - 0.1 > 2)  // La vitesse va descendre jusqu'à 2
                manJSpeed -= 0.1;     // Décrementation de la vitesse
            if (man.getY() > base - 350)
                manLeftY -= manJSpeed;
            else {
                flag = 1;
                man.setBackgroundResource(R.drawable.r1);
            }
        }

        if (flag == 1) {
            if (man.getY() < base) {
                manJSpeed += 0.1;    //Incrémentation de la vitesse (retombé)
                manLeftY += manJSpeed;
            }
            else {
                flag = 2;
                manLeftY = base;
                if (manLeftY == base)
                    manJSpeed = 10;     //Réinitialisation de la vitesse
            }
        }

        if (flag == 3){
            if (man.getX() < screenWidth/3)
                manLeftX += 10;
            else {
                flag = 1;
                man.setBackgroundResource(R.drawable.animation);
                run = (AnimationDrawable) man.getBackground();
                run.start();
            }
        }

        if (man.getX() > 50 && man.getY() == base)
            manLeftX -= 1;

        man.setX(manLeftX);
        man.setY(manLeftY);
    }

    public void colision (){
        obstacle.getHitRect(rc1);
        man.getHitRect(rc2);
        bonus.getHitRect(rc3);

        if (Rect.intersects(rc1, rc2))
            finish();
        if (Rect.intersects(rc3, rc2)) {
            scr ++;
            bonus.setVisibility(View.INVISIBLE);
        }
    }
    private void init () {
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        base = screenHeight/2+150;
        floor2LeftX = screenWidth;
        map2LeftX = screenWidth;
        obstacleLeftX = screenWidth;
        obstacleLeftY = base;
        bonusLeftX = screenWidth;
        manLeftY = base;
        manLeftX = 50;
        flag = 2;
        man.setY(manLeftY);
        man.setX(manLeftX);

        floor.setY(base+75);
        floor2.setY(base+75);
        bonus.setY(base - 200);
    }
}
