package com.study.graphicapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {

    Paint paint;
    int x=400,y=400;
    Thread gameThread;
    boolean gameFlag = true; //쓰레드를 실행할지 여부를 결정

    //자바에서 인스턴스 생성할거면 아래의 생성자만
    public MyView(Context context) {
        super(context);

    }

    //xml에 사용할거면 xml의 속성까지 넘겨받아야하므러 attributeset 이있는 생성자도 정의

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Color.RED);

    }

    //javaee에서는 팔레트가 graphics 가 객체이지만 안드로이드에선 canvas 다
    @Override
    protected void onDraw(Canvas canvas) {
        //사각형 객체 생성
        RectF rectF = new RectF(x,y,x+100,y+100);

        canvas.drawRect(rectF, paint);
    }

    public void gameLoop(){
        while (gameFlag) {
            x += 1;
            y += 1;
            this.invalidate();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

