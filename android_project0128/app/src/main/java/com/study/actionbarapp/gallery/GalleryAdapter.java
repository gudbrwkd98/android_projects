package com.study.actionbarapp.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.actionbarapp.MainActivity;
import com.study.actionbarapp.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {
    //리소스에 있는 자원 이용 네트워크 http 요청 --> 웹서버 구축 ...
    ArrayList<Gallery> galleryArrayList = new ArrayList<Gallery>();
    LayoutInflater layoutInflater;
    MainActivity mainActivity;

    public GalleryAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        layoutInflater = mainActivity.getLayoutInflater(); //액티비티를 통해 인플레이터 얻기
    }

    @Override
    public int getCount() {
        return galleryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return galleryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView == null){
            //false의 의미 지정한 parent 에 부착하지 않고 인플레이션 대상 xml의 최상위를 반환
            view = layoutInflater.inflate(R.layout.item_gallery,parent,false);


        }else{
            view = convertView;
        }
        //결정된 뷰로부터 하위 자식들 접근하기!!
        //ImageView에 자바코드로 이미지 대입

        //Bitmap 객체는 스트림을 통해서도 인스턴스를 얻을수 있기때문에 우리의 경우
        //원격지에 떨어진 웹서버로부터 요청을 통해 얻을수있는 스트림을 이용하려고


        Gallery gallery = galleryArrayList.get(position);
        ImageView image = view.findViewById(R.id.img);
        TextView t_title = view.findViewById(R.id.t_title);

        image.setImageBitmap(gallery.getBitmap());
        t_title.setText(gallery.getTitle());

        image.setOnClickListener(e->{
            Toast.makeText(mainActivity,gallery.getGallery_id()+"선택했어?",Toast.LENGTH_SHORT).show();
        });

        return view;
    }


    }

