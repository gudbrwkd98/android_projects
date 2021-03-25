package com.study.pageapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.study.pageapp.R;

//하나의 화면 일부를 담당하는 Fragment 는 일명 작은 액티비티라고도 불린다
//따라서 액티비티에 생명주기 메서드가 있듯 Fragment 또한 생명주기 메서드가 지원된다..
public class RedFragment extends Fragment {
    //초기화 메서드

    //프레그먼트가 사용할 디자인 레이아웃 xml
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //매개변수로 넘겨받은 인플레이터를 이용하여 xml 인플레이션 하자 !!
        //false 로 지정해야 인프레이션 된 xml 의 최상위 뷰그룹이 ㅂ반환된다..
        //ture 로 지정할시 인플레이셔된 xml 의 최상위 뷰보다 더 바깥쪽 뷰가 반환된다.
        View view = inflater.inflate(R.layout.fragment_red,container,false);

        return view;
    }
}
