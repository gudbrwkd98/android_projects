package com.study.websocketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //웹소켓 객체 선언
    MyWebSocketClient myWebSocketClient;
    BoardDAO boardDAO;
    ListView listView;
    BoardAdapter boardAdapter;
    Handler handler;
    DetailDialog detailDialog; //상세보기 새창
    RegistDialog registDialog; //등록 새창

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        boardAdapter = new BoardAdapter(this);
        listView.setAdapter(boardAdapter);
        handler = new Handler(Looper.getMainLooper()){
            //쓰레드들의 부탁을 받아 대신 UI 제어
            @Override
            public void handleMessage(@NonNull Message message) {
                Bundle bundle = message.getData();
                ArrayList boardList = (ArrayList) bundle.get("boardList");
                boardAdapter.boardList = boardList;
                boardAdapter.notifyDataSetChanged();
                listView.invalidate();
            }
        };
        boardDAO = new BoardDAO(this);
        createSocket();

        //리스트뷰와 리스너 연결
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               getDetail((int)id);
             }
           }
        );



    }

    public void createSocket(){
        try {
            myWebSocketClient = new MyWebSocketClient(new URI("ws://192.168.75.3:9999"),this);
            myWebSocketClient.connect();
            getList();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //앱이 가동됨과 동시에 웹서버에서 목록 가져오기 !
    public void getList(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                boardDAO.selectAll();
            }
        };
        thread.start();

    }

    public void getDetail(int board_id){
        //상세보기 대화상자 띄우기 !! , 새창은 단독으로 존재 할 수 없으므로
        detailDialog = new DetailDialog(this);
        detailDialog.show();

        for (Board board : boardAdapter.boardList){
            if(board.getBoard_id()==board_id){
                detailDialog.setData(board);
                break;
            }
        }


    }

    public void regist(View view){
        registDialog = new RegistDialog(this);
        registDialog.show();
    }

}