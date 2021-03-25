package com.study.actionbarapp.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

//매요청마다 객체가 1대1 대응
//대부분 <URL , 진행상황을 알수있는  Void(Integer),String >
public class ImageDownLoaderAsynce extends AsyncTask<String,Void, Bitmap> {
    GalleryFragment galleryFragment;
    Gallery gallery;
    public ImageDownLoaderAsynce(Gallery gallery,GalleryFragment galleryFragment){
        this.gallery = gallery;
        this.galleryFragment = galleryFragment;
    }

    //웹서버 요청전에 하고싶은 작업은 여기서 UI 제어가능
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //웹서버 요청 환료후에 작업은여기서 (UI 제어가능 )
    @Override
    protected void onPostExecute(Bitmap bitmap) {
       gallery.setBitmap(bitmap);

       galleryFragment.galleryArrayList.add(gallery);
       galleryFragment.galleryAdapter.galleryArrayList=galleryFragment.galleryArrayList; //어댑터의 리스트 교체
       galleryFragment.galleryAdapter.notifyDataSetChanged();
       galleryFragment.gridView.invalidate();
    }

    //요청 도중에 하고싶은 작업 (메인 쓰레드가 수행 UI 제어 가능 )
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    //웹서버 요청 수행 (UI 제어 불가)
    @Override
    protected Bitmap doInBackground(String... param) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(param[0]); //이미지가 있는 웹서버 주소
            InputStream is =  url.openStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
