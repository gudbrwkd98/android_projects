package com.study.pageapp.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.study.pageapp.R;

public class PagerActivity extends AppCompatActivity {
    ViewPager viewPager;
    MyViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        //뷰페이저와 어뎁터 연결
        adapter = new MyViewPagerAdapter(this.getSupportFragmentManager(),0);
        viewPager.setAdapter(adapter);

    }

    public void showPage(View view){
        switch (view.getId()){
            case R.id.red : flowPage(0); break;
            case R.id.blue : flowPage(1); break;
            case R.id.yellow : flowPage(2); break;
        }
    }

    public void flowPage(int position){
        viewPager.setCurrentItem(position,true);
    }


}