package com.study.scheduleapp.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.study.scheduleapp.R;

public class AlphaFragment extends Fragment {
    ImageView imageView;
    Button bt_start;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alpha,container,false);
        imageView = view.findViewById(R.id.imgView);
        bt_start = view.findViewById(R.id.bt_start);

        //버튼누르면 이미지에 애니메이션 적용
        bt_start.setOnClickListener(e->{
            showAni();
        });


        return view;
    }

    public void showAni(){
        //애니메이션 객체 생성
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.ani_alpha);
        //누구에게 ?
        imageView.startAnimation(animation); //적용 및 시작
    }
}
