package com.study.pageapp.intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.study.pageapp.R;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = this.getClass().getName();
    EditText t_id,t_pass,t_name;
    private static final int REQUST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //위젯 버튼과 리스너와의 연결
        Button bt_send = (Button)findViewById(R.id.bt_send);
        Button bt_dial  = (Button)findViewById(R.id.bt_dial);
        Button bt_receive  = (Button)findViewById(R.id.bt_receive);
        t_id = (EditText)findViewById(R.id.t_id);
        t_name = (EditText)findViewById(R.id.t_name);
        t_pass = (EditText)findViewById(R.id.t_pass);

        bt_send.setOnClickListener(this);
        bt_dial.setOnClickListener(this);
        bt_receive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"눌름?");
        if(v.getId() == R.id.bt_send){
            send();
        }else if(v.getId() == R.id.bt_dial){
        callPhone();
        }else if (v.getId() == R.id.bt_receive){
            sendAndGet();
        }

    }
    //다른 액티비티를 호출
    public void send(){
        //아래와 같이 대상 클래스를 정확히 명시하는 인텐트 사용법을 가리켜 명시적 (explicit) 인텐트라 한다
        Intent intent = new Intent(this,ReceiveActivity.class);
       //jsp 의 request session application 객체처럼 데이터를 심을수 있다
        //intent.putExtra("msg","나는 자연인이다");
        Member member = new Member();
        member.setId(t_id.getText().toString());
        member.setPass(t_pass.getText().toString());
        member.setName(t_name.getText().toString());

        //인텐트에 데이터 심기!!
        Bundle bundle = new Bundle();
        bundle.putParcelable("member",member);
        intent.putExtra("data",bundle);


        startActivity(intent);

    }

    public void sendAndGet(){
        Intent intent = new Intent(this,ResultActivity.class);
        Member member = new Member();

        t_id.setText(member.getId());
        t_pass.setText(member.getPass());
        t_name.setText(member.getName());

        Bundle bundle = new Bundle();
        bundle.putParcelable("member",member);
        intent.putExtra("data",bundle);
        //그냥출발이 아니라 결과를 받아올 것을 염두해 둔 출발
       // 매개변수 전달할 데이터 , 요청 구분코드
        startActivityForResult(intent,REQUST_CODE);
    }

    //requestcode 매개변수 : 호출자가 보낸 통신 요청 코드 (독수리 , 1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "" +requestCode);
        if(REQUST_CODE == requestCode) {
            //보장받은 응답..
            if (requestCode == this.RESULT_OK) { //성공의 응답이라면
                Bundle bundle = data.getBundleExtra("data");
                Member member = (Member) bundle.getParcelable("member");
                t_id.setText(member.getId());
                t_pass.setText(member.getPass());
                t_name.setText(member.getName());

            }
        }
    }



    public void callPhone(){
            //우리가 제작중인 현재 앱안에있는 액티비티가 아니라 외부앱의 액티비티명?
            //다른앱의 액티비티는 우리가 알필요가없고 알수도없다
            //암시적 묵시적(implicit) intent 가 자체적으로가진 함수
        Intent intent = new Intent(Intent.ACTION_DIAL);
        startActivity(intent);
    }





}