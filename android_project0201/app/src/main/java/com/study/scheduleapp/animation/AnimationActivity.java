package com.study.scheduleapp.animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.study.scheduleapp.R;

public class AnimationActivity extends AppCompatActivity {
    ViewPager viewPager;
    MyPageAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        viewPager = findViewById(R.id.viewPager);
        adapter = new MyPageAdapter(getSupportFragmentManager(),0);
        viewPager.setAdapter(adapter);//뷰페이저와 어댑터 연결
    }
}