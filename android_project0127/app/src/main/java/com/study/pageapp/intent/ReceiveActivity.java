package com.study.pageapp.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.study.pageapp.R;

public class ReceiveActivity extends AppCompatActivity {
    EditText t_id;
    EditText t_name;
    EditText t_pass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        t_id = (EditText)findViewById(R.id.t_id);
        t_name = (EditText)findViewById(R.id.t_name);
        t_pass = (EditText)findViewById(R.id.t_pass);

        Intent intent =this.getIntent(); //전달받은 기본 인덴트를 참조 포워딩박듯
        Bundle data = intent.getBundleExtra("data");
        Member member = (Member) data.getParcelable("member");

        t_id.setText(member.getId());
        t_pass.setText(member.getPass());
        t_name.setText(member.getName());

    }

    public void close(View view){
        this.finish();
    }


}
