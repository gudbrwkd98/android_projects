package com.study.app0121;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NetworkActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    ConnectManager manager;
    EditText t_id;
    EditText t_pass;
    EditText t_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        //xml 로 부터 안드로이드의 객체들이 인스턴스화된 시점이므로 각 객체에 부여된
        //아이디를 이용하여 레퍼런스를 얻자
        t_id = this.findViewById(R.id.t_id);
        t_pass = this.findViewById(R.id.t_pass);
        t_name = this.findViewById(R.id.t_name);
    }
    //반드시 view 를 매개변수로 넣어줘야 onclick 핸들링이 적용된다
    public void regist(View view){

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(" \"m_id\" : \""+t_id.getText()+"\", ");
        sb.append(" \"m_pass\" : \""+t_pass.getText()+"\", ");
        sb.append(" \"m_name\" : \""+t_name.getText()+"\" ");
        sb.append("}");

        manager = new ConnectManager("http://192.168.75.3:8888/rest/member",sb.toString());
        manager.start();
    }
}