package com.study.pageapp.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    Fragment[] fragments = new Fragment[3]; //총3페이지

    public MyViewPagerAdapter(@NonNull FragmentManager fm, int behavior){
        super(fm,behavior);
        //페이지 생성
        fragments[0] = new RedFragment();
        fragments[1] = new BlueFragment();
        fragments[2] = new YellowFragment();

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
