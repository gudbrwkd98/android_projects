package com.study.actionbarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewpage 는 디자인에 불과하기 때문에 구성 데이터에 대한 정보를 어댑터를 통해 제공받는다!!
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myViewPagerAdapter = new MyViewPagerAdapter(this.getSupportFragmentManager(),0);
        viewPager.setAdapter(myViewPagerAdapter);
    }

    //현재 액티비티에 메뉴를 추가하려면즉 액션바를 추가하려면 인플레이션 시키는 메서드 재정의
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //메뉴전용임플레이터가 따로있다
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.navi,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String msg = null;

        switch (item.getItemId()){
            case R.id.mp3:showPage(0); break;
            case R.id.Chat:showPage(1); break;
            case R.id.gallery:showPage(2); break;
            case R.id.settings:showPage(3); break;
        }

       // Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();


        return true;
    }

    public void showPage(int position){
        viewPager.setCurrentItem(position,true);
    }

}