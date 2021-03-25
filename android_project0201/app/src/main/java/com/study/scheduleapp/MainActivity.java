package com.study.scheduleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.study.scheduleapp.animation.AnimationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //메뉴를 사용할것임 즉 액션바를 활성화 시키지

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
                                //왼쪽 인플레이션 결과를 오른쪽으로 넘기겠다
        getMenuInflater().inflate(R.menu.navi,menu);
        return true;
    }

    //메뉴이벤트 연결

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null; //선언만 하고 의도를 정하지않음
        switch (item.getItemId()){
           case R.id.ani :
               intent = new Intent(this, AnimationActivity.class);
               ;break;
           case R.id.schedule:
               intent = new Intent(this,ScheduleActivity.class);
               ;break;
       }
        startActivity(intent);

        return true;
    }
}