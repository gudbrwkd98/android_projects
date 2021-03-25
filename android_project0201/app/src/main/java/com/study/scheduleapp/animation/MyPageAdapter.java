package com.study.scheduleapp.animation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPageAdapter extends FragmentStatePagerAdapter {
    Fragment[]fragments =   new Fragment[5];
    public MyPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragments[0] = new AlphaFragment();
        fragments[1] = new ScaleFragment();
        fragments[2] = new TranslateFragment();
        fragments[3] = new RotateFragment();
        fragments[4] = new SetFragment();

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
