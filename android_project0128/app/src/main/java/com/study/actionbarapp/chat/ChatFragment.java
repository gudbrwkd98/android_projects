package com.study.actionbarapp.chat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.study.actionbarapp.R;

import java.io.IOException;
import java.net.Socket;

public class ChatFragment extends Fragment {
    Socket socket;
    Thread thread;
    EditText t_ip, t_port;
    TextView t_log;
    EditText t_input;
    ChatThread chatThread;
    Handler handler;
    String TAG = this.getClass().getName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        t_ip = (EditText) view.findViewById(R.id.t_ip);
        t_port = (EditText)view.findViewById(R.id.t_port);
        t_log = (TextView) view.findViewById(R.id.t_log);
        t_input = (EditText)view.findViewById(R.id.t_input);

        //버튼을 얻어와 이벤트 연결
        Button bt_connect = view.findViewById(R.id.bt_connect);
        Button bt_send = view.findViewById(R.id.bt_send);

        bt_connect.setOnClickListener(e->{
          connectServer();
        });

        bt_send.setOnClickListener(e->{
            send();
        });

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message message) {
                //쓰레드가 부족한 UI 제어를 여기서 대신 해준다!!
                Bundle bundle = message.getData();
               String msg =  bundle.getString("msg");
                t_log.append(msg+"\n");
            }
        };



        return view;
    }
    //채팅서버에 접속
    public void connectServer(){
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    socket = new Socket(t_ip.getText().toString(),Integer.parseInt(t_port.getText().toString())); //네트워크 접속 시도하러 출발!!
                    chatThread = new ChatThread(socket,ChatFragment.this);
                    chatThread.start();//청취 시작!!
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    //메시지 보내기!!
    public void send(){
        thread = new Thread(){
            @Override
            public void run() {
                chatThread.send(t_input.getText().toString());
                //핸들러 부탁하여 로그 남기기
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg",t_input.getText().toString());
                message.setData(bundle);

                handler.sendMessage(message);
                Log.d(TAG,t_input.getText().toString());
            }
        };
        thread.start();
    }

}
