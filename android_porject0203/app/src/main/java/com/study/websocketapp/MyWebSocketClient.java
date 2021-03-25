package com.study.websocketapp;

import android.util.Log;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class MyWebSocketClient extends WebSocketClient {
    String TAG = this.getClass().getName();
    MainActivity mainActivity;
    Gson gson = new Gson();

    public MyWebSocketClient(URI serverUri,MainActivity mainActivity) {
        super(serverUri);
        this.mainActivity = mainActivity;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG,"on open called");
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG,"on message called" + message);

        try {
            JSONObject jsonObject = new JSONObject(message); //String - > json object
            if(jsonObject.get("requestCode").equals("Create")){
                mainActivity.boardDAO.selectAll();
            }else if(jsonObject.get("requestCode").equals("Update")){
                mainActivity.boardDAO.selectAll();
            }else if(jsonObject.get("requestCode").equals("Delete")){
                mainActivity.boardDAO.selectAll();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //메시지 보내기
    public void sendMsg(SocketMessage socketMessage){
        String jsonString =  gson.toJson(socketMessage);
        this.send(jsonString);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG,"on close called");
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG,"on error called");
    }
}
