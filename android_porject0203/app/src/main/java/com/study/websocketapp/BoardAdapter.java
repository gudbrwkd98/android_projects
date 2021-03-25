package com.study.websocketapp;

import android.text.method.Touch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends BaseAdapter {

    List<Board> boardList = new ArrayList<Board>();
    LayoutInflater layoutInflater;
    MainActivity mainActivity;

    public  BoardAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.layoutInflater = mainActivity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return boardList.size();
    }

    @Override
    public Object getItem(int position) {
        return boardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return boardList.get(position).getBoard_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(convertView != null){
            view = convertView;
        }else{
            view = layoutInflater.inflate(R.layout.item_board,parent,false);
        }

        //뷰에 데이터 채우기..
        TextView t_title = view.findViewById(R.id.t_title);
        TextView t_writer = view.findViewById(R.id.t_writer);
        TextView t_regdate = view.findViewById(R.id.t_regdate);
        TextView t_hit = view.findViewById(R.id.t_hit);

        //데이터 반영하기
        Board board = boardList.get(position);
        t_title.setText(board.getTitle());
        t_writer.setText(board.getWriter());
        t_regdate.setText(board.getRegdate());
        t_hit.setText(Integer.toString(board.getHit()));


        return view;
    }
}
