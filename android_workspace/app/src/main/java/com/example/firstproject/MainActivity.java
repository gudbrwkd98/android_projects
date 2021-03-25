package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //화면에 버튼 나오게 하자~~!
        //alt + enter  = import
        //ctr + spacce 매개변수 및 etc
        //생성된 메서드 안에서 f2 누를시 안에들어갈 정보가 나옴
        Button bt = new Button(this);
        bt.setText("나의 첫 버튼");

        //화면에 부착
        //this.setContentView(bt);
        this.setContentView(R.layout.linear);
        //결론 : 응용 어플리케이션에서는 화면배치가 중요하다!!

    }
}