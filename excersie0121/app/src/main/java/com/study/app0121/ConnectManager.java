package com.study.app0121;

import android.util.Log;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class ConnectManager extends Thread{
    String TAG = this.getClass().getName();
    URL url;
    HttpURLConnection con ;
    String requestUrl ;
    String data;
    //이객체를 생성하는 자는 주소와 제이슨 데이터를 넘겨야 한다
    public ConnectManager(String requestUrl,String data){
        this.requestUrl = requestUrl;
        this.data = data;
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
            Log.d(TAG,"data response from server : " +sb.toString());
            code = con.getResponseCode(); //reponsed code from server (already done with request & response)
            Log.d(TAG,"code from server : " + code);
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
       int code =  requestByPost();
        Log.d(TAG,"서버로 부터 받은 응답 코드는 : " + code);
    }
}
