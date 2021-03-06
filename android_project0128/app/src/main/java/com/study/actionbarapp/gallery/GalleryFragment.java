package com.study.actionbarapp.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.study.actionbarapp.MainActivity;
import com.study.actionbarapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    String TAG=this.getClass().getName();
    GridView gridView;
    GalleryAdapter galleryAdapter;
    Button bt_load,bt_async;
    ArrayList<Gallery> galleryArrayList;
    Handler handler ;
    ProgressBar progress;
    int totalRecord; //총 레코드 수
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery,container,false);
        gridView = view.findViewById(R.id.gridView);
        bt_load = view.findViewById(R.id.bt_load);
        bt_async = view.findViewById(R.id.bt_async);
        progress = view.findViewById(R.id.progress);
        galleryAdapter = new GalleryAdapter((MainActivity)this.getContext());
        gridView.setAdapter(galleryAdapter);
        galleryArrayList = new ArrayList<Gallery>();

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message message) {
                //개발자가 정의한 쓰레드는 디자인을 제어할 수없으므로 핸들러에게 부탁하자 !!
                galleryAdapter.notifyDataSetChanged(); //어댑터 다시 동작
                gridView.invalidate(); //UI 갱신

                if(totalRecord >= galleryArrayList.size()){
                    progress.setVisibility(view.INVISIBLE);
                }
            }
        };

        bt_load.setOnClickListener(e->{
            //프로그래스바 보이게
            progress.setVisibility(view.VISIBLE);
            //웹서버로 부터 제이슨을 받아와 함
            galleryArrayList.removeAll(galleryArrayList); //기존 데이터 요소 모두 삭제
            getList();
            //받아온 이미지 파일명을 이용하여 웹서버에 이미지 요청 !!
           //load("http://192.168.75.3:7777/images/1.png");


        });

        bt_async.setOnClickListener(e->{
            //프로그래스바 보이게
            progress.setVisibility(view.VISIBLE);
            galleryArrayList.removeAll(galleryArrayList); //기존 데이터 요소 모두 삭제
            getListByAsynce();
        });

        return view;
    }
    /*------------------------------------
    쓰레드와 핸들러를 이용하여 직접 구현한 코드영역
    //웹서버로부터 데이터베이스의 정보를 가져오자!!
    --------------------------------------*/
    public void getList(){
        Thread thread = new Thread(){
            public void run() {
                BufferedReader buffr=null;
                try {
                    URL url=new URL("http://192.168.75.3:7777/gallery");
                    HttpURLConnection con=(HttpURLConnection) url.openConnection();
                    buffr = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                    StringBuilder sb = new StringBuilder(); //data값이 누적될 객체선언
                    String data =null;
                    while(true){
                        data = buffr.readLine();
                        if(data==null)break;
                        sb.append(data);
                    }
                    Log.d(TAG, sb.toString());
                    con.getResponseCode(); //요청과 응답이 이루어짐..
                    //서버와 연결이 이미 끊긴 시점..
                    //서버로 부터 가져온 제이슨 배열만큼 이미지 로드 메서드를 호출!!!
                    try {
                        JSONArray jsonArray = new JSONArray(sb.toString());
                        totalRecord = jsonArray.length(); //총 레코드 수 보관
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject json =(JSONObject) jsonArray.get(i);
                            String filename = json.getString("filename");
                            load(filename); //이미지 한개를 서버로 부터 가져온후, 어댑터의 리스트에 추가!!
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    if(buffr!=null){
                        try {
                            buffr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start(); //웹서버 요청 시작!!!
    }

    //네트워크상 웹서버에 접속하여 이미지를 가져오자!!
    public void load(String image){

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.75.3:7777/images/"+image);
                    InputStream is = url.openStream(); //지정한 URL 자원에 대한 스트림을 취득!!
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Gallery gallery = new Gallery();
                    gallery.setBitmap(bitmap);
                    galleryArrayList.add(gallery);
                    galleryAdapter.galleryArrayList = galleryArrayList; //어댑터의 데이터에 대입

                    handler.sendEmptyMessage(0);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }

        /*------------------------------------
    Async Class 를 이용하여 직접 구현한 코드영역
    //웹서버로부터 데이터베이스의 정보를 가져오자!!
    --------------------------------------*/
    public void getListByAsynce(){
        MyAsync myAsync = new MyAsync(this); //비동기 객체 생성

        //백그라운드로 작업 시작!! 이떄 doInBackgorund 메서드가 호출됨 쓰레드로 따라서 네트워크 요청 등에 사용
        myAsync.execute("http://192.168.75.3:7777/gallery");
    }

}
