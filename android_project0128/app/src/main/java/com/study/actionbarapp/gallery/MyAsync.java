package com.study.actionbarapp.gallery;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//제이쿼리 Jquery ajax처럼 안드로이드에서도 웹서버와 통신시
public class MyAsync extends AsyncTask<String,Void,String> {
    GalleryFragment galleryFragment;

    public MyAsync(GalleryFragment galleryFragment){
        this.galleryFragment = galleryFragment;
    }

    //비동기 요청전에 하고싶은작업  (메인쓰레드로 수행 : UI 제어가능)
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //비동기 요청 (쓰레드로 수행)
    //주로 네트워크 요청시 사용됨..
    @Override
    protected String doInBackground(String... params) {
        String requestUrl = params[0]; //웹서버 요청 주소

        BufferedReader buffr = null;
        StringBuilder sb = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            buffr = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

            sb = new StringBuilder();
            String data = null;
            while(true){
                data = buffr.readLine();
                if(data == null) break;
                sb.append(data);
            }
            con.getResponseCode();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(buffr != null){
                try {
                    buffr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("test","데이터 !!!! "+ sb.toString());
        return sb.toString();
    }


    //요청하는 중에 처리할 작업  (메인쓰레드로 수행 : UI 제어가능)
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    //요청받은 후 응답데이터 처리 (메인쓰레드로 수행 : UI 제어가능)
    @Override
    protected void onPostExecute(String data) {
        //이메서드는 메인쓰레드에 의해 수행되므로 여기서 UI 도 제어할수있다..
        //즉 핸들러가 필요없다

        try {
            JSONArray jsonArray = new JSONArray(data);
            ArrayList<Gallery> arrayList = new ArrayList<Gallery>();
            for(int i  = 0 ; i < jsonArray.length(); i ++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Gallery gallery = new Gallery();
                gallery.setFilename(jsonObject.getString("filename"));
                gallery.setGallery_id(jsonObject.getInt("gallery_id"));
                gallery.setTitle(jsonObject.getString("title"));
                //웹서버에서 이미지 가져와서 비트맵으로 반환..
                ImageDownLoaderAsynce downLoaderAsynce = new ImageDownLoaderAsynce(gallery,galleryFragment);
                downLoaderAsynce.execute("http://192.168.75.3:7777/images/"+gallery.getFilename());

                arrayList.add(gallery); //리스트에 추가!!
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
