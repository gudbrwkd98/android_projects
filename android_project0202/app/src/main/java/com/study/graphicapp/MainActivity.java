package com.study.graphicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    MyView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = findViewById(R.id.myView);
    }

    public void move(View view){
//        myView.x +=10;
//        myView.y += 10;
//        //자바의 repaint -> 뷰.invalidate
//        myView.invalidate(); //해당뷰의 ondraw 호출
        myView.gameFlag = true;
        Thread gameThread = new Thread(){
            @Override
            public void run() {
               myView.gameLoop();
            }
        };
        gameThread.start();
    }

    public  void stop(View view){
        myView.gameFlag = false;
    }


}