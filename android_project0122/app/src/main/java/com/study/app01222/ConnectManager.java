package com.study.app01222;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//안드로이드는 메인 쓰레드가 루프나 대기상태나 웹 요청등을 수행하면 그 자체로 에러 사항이다!!
//왜? 메인 쓰레드는 무조건 UI , Event 등을 처리해야 하는 앱운영 쓰레드 이므로..
//만일 위와같은 금지된 작업을 수행하면 앱은 무한 대기 상태에 빠지게 되므로..
public class ConnectManager extends Thread{
    String TAG = this.getClass().getName();
    URL url;
    HttpURLConnection con ;
    String requestUrl ;
    String data;
    MainActivity mainActivity;
    //이객체를 생성하는 자는 주소와 제이슨 데이터를 넘겨야 한다
    public ConnectManager(MainActivity mainActivity,String requestUrl, String data){
        this.requestUrl = requestUrl;
        this.data = data;
        this.mainActivity = mainActivity;
    }


    public int requestByGet(){
        BufferedReader buffr = null;
        int code = 0;
        try{
            url = new URL(requestUrl);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");

            //getting response data from server
            buffr = new BufferedReader(new InputStreamReader(con.getInputStream())); //stream based on bite
            StringBuilder sb = new StringBuilder();
            String data = null;
            while(true){
                data = buffr.readLine();
                if(data == null) break; //if there's no more data
                sb.append(data);
            }

            code = con.getResponseCode(); //reponsed code from server (already done with request & response)
            Log.d(TAG,"code from server : " + code);

            //이시점이 바로 통신이 완료된 시점
            Log.d(TAG,"data response from server : " +sb.toString());
            //사용자 정의 쓰레드는 디자인 접근 불가
            //해결책 디자인 갱신
            //핸들러에게 요청
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("data",sb.toString());
            message.setData(bundle);
            mainActivity.handler.sendMessage(message);//handler의 handlemessage()를 호출하게됨
            //mainActivity.printData(null);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(buffr!=null){
                try{
                    buffr.close();
                }catch(IOException e){

                }
            }
        }
        return code;
    }

    //Post method and sending JSON data
    public int requestByPost(){
        BufferedWriter buffw = null; //bufferedwriter string stream
        int code = 0 ;
        try{

            url = new URL(requestUrl);
            con = (HttpURLConnection)url.openConnection();
            //should incude data-type so we could send JSON type data HTTP protocols rule
            con.setRequestProperty("Content-Type","application/json;charset=utf-8");
            con.setRequestMethod("POST");
            con.setDoOutput(true); //allowed output date to server
            //before send request prepare body form , make json string
            //making String type of JSON not object
		/*
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(" \"m_id\" : \"BATMAN\", ");
		sb.append(" \"m_pass\" : \"1234\", ");
		sb.append(" \"m_name\" : \"superman\" ");
		sb.append("}");
		*/

            //using outputstream
            buffw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"UTF-8"));
            buffw.write(data);
            buffw.flush();
            code = con.getResponseCode(); //reponse + request
            System.out.println("response code : " + code);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return code;
    }

    @Override
    public void run() {
       int code =  requestByGet();
        Log.d(TAG,"서버로 부터 받은 응답 코드는 : " + code);
    }
}
