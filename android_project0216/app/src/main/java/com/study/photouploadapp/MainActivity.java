package com.study.photouploadapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CAMERA = 1 ;
    ImageView imageView;
    Bitmap bitmap; //현재보고있는 사진에대한 비트맵
    String TAG = this.getClass().getName();
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }


    //권한 승인


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CAMERA){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                callCamera();
            }else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("권한 안내").setMessage("서비스 이용을 위해서는 카메라 권한을 승인하셔야 합니다").create().show();
            }
        }
    }

    @Override//다른 액티비티 호출후 그 결과를 가져올떄 호출
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CAMERA){ //카메라에 대한 요청었다면
            //유저가 촬영한 사진 가져오기!!
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    public void callCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA); //호출후 결과를 가져옴
    }

    public void take(View view){
            //사진 촬영은 Dangerous 한 권한이므로 권한 요청을 해야한다
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CAMERA
        },REQUEST_CAMERA); //이메서드 호출에 의해 권한 승인 관련 팝업이 뜬다!!
    }

    //스토리지에 우리만의 폴더를 생성하고 그안에 이미지를 저장하자
    public void save(View view){
        File folder = new File(this.getExternalFilesDir(null),"pic");
        folder.mkdir(); //위에서 명시한 경로에 디렉토리 생성

        String filename = System.currentTimeMillis()+".jpg"; //사진파일명 만들기

       file = new File(folder,filename);

        FileOutputStream fos = null;

        try {
            fos= new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            Log.d(TAG,"파일이있나요?" + file.exists());
            Toast.makeText(this,"이미지가 저장되었습니다",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void send(View view){
        Thread thread = new Thread(){
            @Override
            public void run() {
                upload();
            }
        };
        thread.start();
    }

    public void upload(){
        String boundary ="gewreqwrasasdds";
        String line="/r/n";
        BufferedWriter buffw = null; //파일의 정보를 전송할 스트림
        OutputStream os = null; //파일을 전송할 스트림
        FileInputStream fis = null;
        try {
            URL url = new URL("http://192.168.75.1:9999/admin/photo");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestProperty("Content-Type","multipart/form-data;charset=utf-8; boundary="+boundary);
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            buffw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"UTF-8"));

            buffw.append("--"+boundary).append(line);
            buffw.append("Content-Type:text/plain/charset=utf-8").append(line);
            buffw.append("Content-Disposition:form-data; name=\"title\"  ").append(line);
            buffw.append(line);
            buffw.append("연습이에용&&").append(line);
            buffw.flush();


            buffw.append("--"+boundary).append(line);
            buffw.append("Content-Type:"+ URLConnection.guessContentTypeFromName(file.getName())).append(line);
            buffw.append("Content-Disposition:form-data; name=\"myFile\";filename=\""+file.getName()+"\"  ").append(line);
            buffw.append("Content-Transfer-Encoding:binary").append(line);
            buffw.append(line);
            buffw.flush();


            fis = new FileInputStream(file); //전송할 파일을 읽기위한 스트림!!
            os = con.getOutputStream();
            int data = -1;
            byte[] buffer = new byte[1024];
            while(true){
                data = fis.read(buffer);
                if(data == -1)break;

                os.write(data);

            }

            buffw.append(line);
            buffw.append("-"+boundary+"--").append(line);



            int code = con.getResponseCode(); //요청 및 응답 발생
            Log.d(TAG,"서버의 응답코드" + code);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void upload2(){
        String boundary = "^-----^";
        String LINE_FEED = "\r\n";
        String charset = "UTF-8";
        OutputStream outputStream;
        PrintWriter writer;

        JSONObject result = null;
        try{

            URL url = new URL("http://192.168.75.1:9999/admin/photo");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + boundary);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);

            outputStream = connection.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

            /** Body에 데이터를 넣어줘야 할경우 없으면 Pass **/
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"title\"").append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.append("batman").append(LINE_FEED);
            writer.flush();

            /** 파일 데이터를 넣는 부분**/
            Log.d(TAG, "업로드할 파일명"+file.getName());

            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"myFile\"; filename=\"" + file.getName() + "\"").append(LINE_FEED);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead = -1;

            while (true) {
                bytesRead = fis.read(buffer);
                if(bytesRead==-1)break;
                outputStream.write(buffer);
            }
            outputStream.flush();
            fis.close();
            writer.append(LINE_FEED);
            writer.flush();

            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            int responseCode = connection.getResponseCode();
            Log.d(TAG, "요청 시도 결과 responseCode " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                try {
                    result = new JSONObject(response.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                result = new JSONObject(response.toString());
            }

        } catch (ConnectException e) {
            Log.e(TAG, "ConnectException");
            e.printStackTrace();


        } catch (Exception e){
            e.printStackTrace();
        }
    }

}