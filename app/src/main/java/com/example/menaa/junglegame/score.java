package com.example.menaa.junglegame;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class score extends AppCompatActivity {
    private String[] tab = new String[5];
    private int[] tabInt = new int[5];
    private TextView scrd;
    private String texte = "";
    private String FILENAME = "memo";
    private Button reset;
    private LinkedList<String> data = new LinkedList<String>();

    //private LinkedList<String> data = new LinkedList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scrd = (TextView) findViewById(R.id.scrd);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texte = "";
                data.clear();
                for (int i = 0 ; i < tab.length ; i++){
                    data.add(new String(0 +"\n"));
                }
                saveData();
                display();
            }
        });
        display();

    }
    private void display(){
        int cmp = 0;

        try {
            InputStream fis = openFileInput(FILENAME);
            BufferedReader r = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = r.readLine()) != null) {
                if (line != null)
                    tab[cmp] = line;
                    tabInt[cmp] = getInt(line);
                cmp++;
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tri();
        for (int i = 0 ; i < tabInt.length ; i++){
            texte = texte +  (i+1) + ". " + tabInt[i] + "\n";
        }
        scrd.setText(texte);
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

    public int getInt(String s){
        return Integer.parseInt(s.replaceAll("[\\D]", ""));
    }

    public void tri() {
        int longueur = tabInt.length;
        int tampon = 0;
        boolean permut;

        do {
            permut = false;
            for (int i = 0; i < longueur - 1; i++) {
                if (tabInt[i] < tabInt[i + 1]) {
                    tampon = tabInt[i];
                    tabInt[i] = tabInt[i + 1];
                    tabInt[i + 1] = tampon;
                    permut = true;
                }
            }
        } while (permut);
    }


}