package com.study.scheduleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


    }
    //메인 액티비티 호출
    public void goMain(View view){
                        //매개변수 : 어디에서 ,어디로
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent); //지정한 액티비티 호출
        
    }
}