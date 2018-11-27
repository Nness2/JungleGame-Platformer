package com.example.menaa.junglegame;

import android.content.Context;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.AnnotatedElement;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class game extends AppCompatActivity {
    AnimationDrawable run;
    AnimationDrawable runEnemy;

    private Object man;
    private Object bonus;
    private Object enemy;
    private Object obstacle;
    private Object map2;
    private Object floor;
    private Object floor2;
    private Object map;
    private Object hitbox;
    private int prev;

    private int screenWidth;
    private int screenHeight;
    private float manJSpeed = 10;
    private int obSpeed = 5;
    private int flag;
    private int isRun = 0;
    private TextView score;
    private int scr;
    private ImageView menu;
    private Random rand = new Random();
    private float base;
    private int state = 1;
    private int scoreState = 1;
    private int[] tab = new int[10];
    private String FILENAME = "memo";
    private LinkedList<String> data = new LinkedList<String>();
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private Rect rc1 = new Rect();
    private Rect rc2 = new Rect();
    private Rect rc3 = new Rect();
    private Rect rc4 = new Rect();
        private Button jump;
    private Button brk;
    private int initialize = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getScore();
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        brk = (Button) findViewById(R.id.brk);
        man = new Object(50, 0, (ImageView) findViewById(R.id.man));
        map = new Object(0, 0, (ImageView) findViewById(R.id.map));
        map2 = new Object(screenWidth, 0, (ImageView) findViewById(R.id.map2));
        floor = new Object(0, 0, (ImageView) findViewById(R.id.floor));
        floor2 = new Object(screenWidth, 0, (ImageView) findViewById(R.id.floor2));
        obstacle = new Object(screenWidth*2, 0, (ImageView) findViewById(R.id.obstacle));
        bonus = new Object( (((rand.nextInt(1)+2)*screenWidth)), 0, (ImageView) findViewById(R.id.bonus));
        enemy = new Object( (((rand.nextInt(4)+3)*screenWidth)), 0, (ImageView) findViewById(R.id.enemy));
        hitbox = new Object( (((rand.nextInt(4)+3)*screenWidth)), 0, (ImageView) findViewById(R.id.hitbox));
        score = (TextView) findViewById(R.id.score);
        jump = (Button) findViewById(R.id.jump);

        menu = (ImageView) findViewById(R.id.menu);


        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 1) {
                    isRun = 0;
                    if (man.img.getY() == screenHeight - base*3)
                        flag = 0;

                    else
                        flag = 3;
                }
            }
        });

        brk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (state == 1) {
                    menu.setVisibility(View.VISIBLE);
                    brk.setBackgroundResource(R.drawable.play);
                    man.img.setBackgroundResource(R.drawable.r3);

                    state = 0;
                    if (flag == 2) {
                        man.img.setBackgroundResource(R.drawable.r3);
                        isRun = 0;
                    }
                }
                else{
                    menu.setVisibility(View.INVISIBLE);
                    brk.setBackgroundResource(R.drawable.pause);
                    state = 1;
                }
            }
        });

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (initialize == 0){
                            init();
                            initialize = 1;
                        }
                        if (state == 1) {
                            mvBackGround();
                            mvObstacle();
                            colision();
                        }
                        mvJump();
                    }
                });
            }
        }, 0, 10);

    }

    private void mvBackGround(){
        map.x -= 1;
        if (map.img.getX() + map.img.getWidth() < 0){
            map.x = screenWidth-15;
        }

        map2.x -= 1;
        if (map2.img.getX() + map2.img.getWidth() < 0){
            map2.x = screenWidth-15;
        }
        map.img.setX(map.x);
        map2.img.setX(map2.x);

    }

    private void mvObstacle() {
        scr++;
        score.setText("SCORE : " + Integer.toString(scr));
        if (scr % 1000 == 0) {
            obSpeed += 1;
        }
        obstacle.x -= obSpeed;
        bonus.x -= obSpeed;
        enemy.x -= obSpeed+1.25;
        floor.x -= obSpeed;
        floor2.x -= obSpeed;
        if (obstacle.img.getX() + obstacle.img.getWidth() < 0)
            obstacle.x = screenWidth + (rand.nextInt(4)+1)*base;

        if (bonus.img.getX() + bonus.img.getWidth() < 0){
            bonus.x = screenWidth + (((rand.nextInt(2)+1)*screenWidth));
            bonus.img.setBackgroundResource(R.drawable.d1);
            scoreState = 1;
        }

        if (enemy.img.getX() + enemy.img.getWidth() < 0){
            enemy.x = screenWidth + (((rand.nextInt(4)+3)*screenWidth));
        }

        if (floor.img.getX() + floor.img.getWidth() < 0){
            floor2.x = 0;
            floor.x = screenWidth-15;
        }

        if (floor2.img.getX() + floor2.img.getWidth() < 0){
            floor2.x = screenWidth-15;
            floor.x = 0;
        }

        if (man.img.getX() > 50 && man.img.getY() == screenHeight -base *3)
            man.x -= 1;

        enemy.img.setX(enemy.x);
        hitbox.img.setX(enemy.x);
        obstacle.img.setX(obstacle.x);
        floor.img.setX(floor.x);
        floor2.img.setX(floor2.x);
        bonus.img.setX(bonus.x);
    }

    private void mvJump(){

        if (flag == 0){
            if (prev != flag) {
                man.img.setBackgroundResource(R.drawable.r2);
                prev = flag;
            }

            if (manJSpeed - 0.1 > 2)  // La vitesse va descendre jusqu'à 2
                manJSpeed -= 0.1;     // Décrementation de la vitesse
            if (man.img.getY() > screenHeight - (base*7))
                man.y -= manJSpeed;
            else {
                flag = 1;
            }
        }

        if (flag == 1) {
            if (prev != flag) {
                man.img.setBackgroundResource(R.drawable.r1);
                prev = flag;
            }
            if (man.img.getY() < screenHeight - base*3) {
                manJSpeed += 0.1;    //Incrémentation de la vitesse (retombé)
                man.y += manJSpeed;
            }
            else {
                flag = 2;
                man.y = screenHeight - base*3;
                if (man.y == screenHeight - base*3)
                    manJSpeed = 10;     //Réinitialisation de la vitesse
            }
        }

        if (flag == 2 && isRun == 0 && state == 1){
            isRun = 1;
            man.img.setBackgroundResource(R.drawable.animation);
            run = (AnimationDrawable) man.img.getBackground();
            run.start();
        }


        if (flag == 3){
            if (prev != flag) {
                man.img.setBackgroundResource(R.drawable.r10);
                prev = flag;
            }
            if (man.img.getX() < screenWidth/3)
                man.x += 10;
            else
                flag = 1;
        }

        if (flag == 4) {
            if (prev != flag) {
                man.img.setBackgroundResource(R.drawable.r4);
                prev = flag;
            }


            if (man.y > screenHeight - base*4)
                man.y -= 5;
            else
                flag = 5;
        }

        if (flag == 5){
            man.y += 5;
            if (man.y > screenHeight)
                finish();
        }

        man.img.setX(man.x);
        man.img.setY(man.y);
    }

    private int sort (){
        int index = 0;
        int mini = tab[0];
        for (int i = 0 ; i < 5 ; i++){
            if (tab[i] == 0){
                index = i;
                break;
            }
            if (mini > tab[i]){
                mini = tab[i];
                index = i;
            }
        }
        return index;
    }

    private void colision (){
        obstacle.img.getHitRect(rc1);
        man.img.getHitRect(rc2);
        bonus.img.getHitRect(rc3);
        hitbox.img.getHitRect(rc4);

        if (Rect.intersects(rc1, rc2) || Rect.intersects(rc2, rc4)){
            state = 0;
            if (tab[sort()] < scr)
                tab[sort()] = scr;
            data.clear();
            for (int i = 0 ; i < 5 ; i++)
                data.add(new String(tab[i]+"\n"));
            saveData();
            flag = 4;
        }

        if (Rect.intersects(rc3, rc2)) {
            if (scoreState == 1){
                scr += 100;
                scoreState = 0;
            }
            bonus.img.setBackgroundResource(R.drawable.cent);
        }
    }

    private void init () {
        map.img.setY(map.y);
        map2.img.setY(map2.y);
        base = man.img.getHeight();
        man.y = screenHeight - base*3;
        enemy.img.setY(screenHeight - base*6);
        hitbox.img.setY(screenHeight - base*6);
        bonus.img.setY(screenHeight - base*5);
        obstacle.img.setY(screenHeight - base*3);
        floor.img.setY(screenHeight - base*2);
        floor2.img.setY(screenHeight - base*2);
        enemy.img.setBackgroundResource(R.drawable.enemyanimation);
        runEnemy = (AnimationDrawable) enemy.img.getBackground();
        runEnemy.start();
        flag = 2;
        prev = -1;
        menu.setVisibility(View.INVISIBLE);
    }


    private void saveData() {
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

    private int getInt(String s){
        return Integer.parseInt(s.replaceAll("[\\D]", ""));
    }

    private void getScore(){
        int cmp = 0;

        try {
            InputStream fis = openFileInput(FILENAME);
            BufferedReader r = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = r.readLine()) != null) {
                tab[cmp] = getInt(line);
                cmp++;
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
