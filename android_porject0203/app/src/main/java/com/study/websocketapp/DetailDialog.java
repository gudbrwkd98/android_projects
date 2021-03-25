package com.study.websocketapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;

public class DetailDialog extends Dialog {
    Button bt_edit,bt_del;
    EditText t_title,t_writer,t_content;
    int board_id;
    MainActivity mainActivity;
    public DetailDialog(@NonNull Context context) {
        super(context);
        this.mainActivity = (MainActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //디자인된 xml을 인플레이션
        setContentView(R.layout.dialog_detail);
        bt_edit = findViewById(R.id.bt_edit);
        bt_del = findViewById(R.id.bt_del);

        t_title = findViewById(R.id.t_title);
        t_writer = findViewById(R.id.t_writer);
        t_content = findViewById(R.id.t_content);

        bt_edit.setOnClickListener(e->{
            edit();
        });

        bt_del.setOnClickListener(e->{
            del();
        });
    }

    public void setData(Board board){
        t_title.setText(board.getTitle());
        t_writer.setText(board.getWriter());
        t_content.setText(board.getContent());
        board_id = board.getBoard_id();
    }

    public void edit(){

        //유저가 수정한 데이터 vo 로 담기!!
        Board board = new Board();
        board.setBoard_id(board_id);
        board.setTitle(t_title.getText().toString());
        board.setWriter(t_writer.getText().toString());
        board.setContent(t_content.getText().toString());

        Thread thread = new Thread(){
            @Override
            public void run() {
                mainActivity.boardDAO.edit(board);
            }
        };

        thread.start();

    }

    public void del(){

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    mainActivity.boardDAO.del(board_id); //한건삭제
                }catch (BoardUpdateException e){
                    e.getMessage();
                }
            }
        };

        thread.start();

    }

}
