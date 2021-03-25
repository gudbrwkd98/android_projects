package com.study.graphicapp.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.study.graphicapp.R;

public class GalleryActivity extends AppCompatActivity {
    GalleryView galleryView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        galleryView = findViewById(R.id.galleryView);
    }

    public void next(View view){
        if(galleryView.index < galleryView.imageURL.length-1) { //배열보다 작을때까지만
            galleryView.index++;
            galleryView.loadImage(galleryView.imageURL[galleryView.index]);
            galleryView.invalidate();
        }else{
            Toast.makeText(this,"더이상 이미지 가 없습니다",Toast.LENGTH_SHORT).show();
        }
    }

    public void prev(View view){
        if(galleryView.index > 0) { //배열보다 작을때까지만
            galleryView.index--;
            galleryView.loadImage(galleryView.imageURL[galleryView.index]);
            galleryView.invalidate();
        }else{
            Toast.makeText(this,"더이상 이미지 가 없습니다",Toast.LENGTH_SHORT).show();
        }
    }

    public void stop(View view){

    }

    public void auto(View view){
        Thread thread = new Thread(){
            @Override
            public void run() {
                for(int i =0 ; i < galleryView.imageURL.length-1;i++){
                    galleryView.index++;
                    galleryView.loadImage(galleryView.imageURL[galleryView.index]);
                    galleryView.invalidate();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

    }


}