package com.study.websocketapp;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    String TAG = this.getClass().getName();
    String ip = "192.168.75.3";
    int port = 3000;
    Gson gson = new Gson();
   MainActivity mainActivity;
   public BoardDAO(MainActivity mainActivity){
       this.mainActivity = mainActivity;
   }

    //목록
    public void selectAll() throws BoardUpdateException{
        //쓰레드 작업후 UI 제어 요청
        String uri = "/board";
        BufferedReader buffr = null;
        try {
            URL url = new URL("http://"+ip+":"+port+uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //연결이 끊어지기 전에 스트림으로 데이터 가져오기
            buffr = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String data = null; //한줄을 받을 임시 데이터
            while(true){
                data = buffr.readLine();
                if(data==null)break;
                sb.append(data);
            }
           int code = connection.getResponseCode(); //요청 및 응답 자바의 객체화
            if(code != 200){
                throw new BoardUpdateException("목록 조회 오류");
            }

            Log.d(TAG,sb.toString());

            try {
                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONArray jsonArray =  (JSONArray) jsonObject.get("data");

                //jsonarray 에서 java의 object list 로 변환
                ArrayList<Board> boardList = new ArrayList<Board>();
                for(int i = 0 ; i < jsonArray.length();i++){
                    JSONObject json = (JSONObject)jsonArray.get(i);
                    Board board = gson.fromJson(json.toString(),Board.class);
                   // Log.d(TAG,"테스트 "+ board.getWriter());
                    boardList.add(board);

                }
                Log.d(TAG,"테스트 "+  boardList.size());
                //어뎁터의 list값을 변경
                //핸들러에게 전달 UI 갱신을 위해
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("boardList",boardList);
                message.setData(bundle);
                mainActivity.handler.sendMessage(message);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(buffr != null){
                try {
                    buffr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //상세보기


    //등록
    public void insert(Board board) throws BoardUpdateException{
        String uri = "/board";
        BufferedWriter buffw = null; //데이터 전송용 스트림
        try {
            URL url = new URL("http:\\"+ip+":"+port+uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json;charset=utf-8");
            con.setDoOutput(true);
            //보낼 데이터 구성
            buffw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"UTF-8"));


            String  jsonString = gson.toJson(board);

            buffw.write(jsonString);
            buffw.flush();

            int code = con.getResponseCode();

            if(code!=200){
                throw new BoardUpdateException("등록 실패");
            }

            //다이얼로그 창 닫기!
            mainActivity.detailDialog.dismiss();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(buffw!=null){
                try {
                    buffw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    //수정
    public void edit(Board board) throws BoardUpdateException{
       String uri = "/board";
       BufferedWriter buffw = null; //데이터 전송용 스트림
        try {
            URL url = new URL("http:\\"+ip+":"+port+uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type","application/json;charset=utf-8");
            con.setDoOutput(true);
            buffw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"UTF-8"));
            String jsonString = gson.toJson(board);

            buffw.write(jsonString);
            buffw.flush();

            int code = con.getResponseCode(); //요청 및 응답 자바의 객체화
            if(code != 200){
                throw new BoardUpdateException("목록 조회 오류");
            }
            //서버에 수정했음을 알리자!
            SocketMessage socketMessage = new SocketMessage();
            socketMessage.setRequestCode("Update");//CRUD중 UPDate
            socketMessage.setData(jsonString);

            mainActivity.myWebSocketClient.sendMsg(socketMessage);
            //다이얼로그 창 닫기!
            mainActivity.detailDialog.dismiss();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(buffw!=null){
                try {
                    buffw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //삭제
    public void del(int board_id) throws BoardUpdateException{
        String uri = "/board/"+board_id;
        try {
            URL url = new URL("http://"+ip+":"+port+uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");

           int code =  con.getResponseCode();

           if(code != 200){
               throw new BoardUpdateException("삭제 실패");
           }
            //다이얼로그 창 닫기!
            mainActivity.detailDialog.dismiss();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
