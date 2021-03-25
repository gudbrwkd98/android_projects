package com.study.graphicapp.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GalleryView extends View {
    int index = 0;
    String [] imageURL={"https://newsimg.hankookilbo.com/cms/articlerelease/2020/09/13/c22a51dd-d1f8-48d2-82ad-f18a671078be.jpg",
    "https://s3.ap-northeast-2.amazonaws.com/elasticbeanstalk-ap-northeast-2-176213403491/media/magazine_img/magazine_319/112_%E1%84%8A%E1%85%A5%E1%86%B7%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AF.jpg",
            "http://img.khan.co.kr/news/2019/11/29/l_2019112901003607500286631.jpg","http://image.dongascience.com/Photo/2020/03/5bddba7b6574b95d37b6079c199d7101.jpg",
            "https://s3.ap-northeast-2.amazonaws.com/elasticbeanstalk-ap-northeast-2-176213403491/media/magazine_img/magazine_262/%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg",
            "http://www.sisa-news.com/data/photos/20200936/art_159912317533_32480a.jpg","https://i.pinimg.com/originals/44/03/6c/44036c1008f4764c7113a54cf5e5fcf0.jpg",
            "https://t1.daumcdn.net/liveboard/holapet/0e5f90af436e4c218343073164a5f657.JPG",
            "https://cdn.imweb.me/thumbnail/20200827/07f5505e49bc8.png","https://blog.kakaocdn.net/dn/k7Dpb/btqD3dAwwLB/kh7TfXzCGOa6aKRoSxiuC1/img.jpg",};

    Bitmap bitmap;


    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadImage(imageURL[index]);

    }

    public void loadImage(String path){

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    bitmap = BitmapFactory.decodeStream(url.openStream());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
         canvas.drawBitmap(bitmap,0,0,null);
    }
}
