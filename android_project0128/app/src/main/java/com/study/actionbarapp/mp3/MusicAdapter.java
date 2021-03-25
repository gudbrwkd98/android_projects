package com.study.actionbarapp.mp3;

import android.media.Image;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.actionbarapp.MainActivity;
import com.study.actionbarapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*ListView에게 데이터 정보를 제공할 어댑터 정의*/
public class MusicAdapter extends BaseAdapter {
    String TAG = this.getClass().getName();
    String[] title ={"음악파일1.mp3","음악파일2.mp3","음악파일3.mp3"};
    int[] files= {R.raw.music1, R.raw.music2, R.raw.music3};
    List<Music> musicList = new ArrayList<Music>();
    HashMap<Integer, MediaPlayer> map = new HashMap<Integer, MediaPlayer>();

    LayoutInflater layoutInflater;
    MainActivity mainActivity;
    MusicFragment musicFragment;
    MediaPlayer mediaPlayer;

    public MusicAdapter(MainActivity mainActivity,MusicFragment musicFragment){
        this.mainActivity = mainActivity;
        layoutInflater = mainActivity.getLayoutInflater();
        this.musicFragment  = musicFragment;
        //리스트 구성하기
        int[] files = {R.raw.music1,R.raw.music2,R.raw.music3};
        for(int i =0 ; i < files.length;i++){
            Music music = new Music();
            music.setTitle("android music" + i);
            music.setFile(files[i]);
            musicList.add(music);
        }

    }

    //몇건?
    public int getCount() {
        return musicList.size();
    }

    //지정한 위치의 데이터 반환
    public Object getItem(int position) {
        return musicList.get(position);
    }

    //지정한 위치의 아이디 반환(식별값:개발자가 결정)
    public long getItemId(int position) {
        return 0; //pk
    }

    //지정한 위치에 들어갈 뷰 반환
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        Music music = musicList.get(position);
        if(convertView==null){//최초로 등장하는 아이템
            view = layoutInflater.inflate(R.layout.item_music, parent, false);

        }else{
            view = convertView;
        }
        TextView t_title = view.findViewById(R.id.t_title);
        t_title.setText(music.getTitle());

        //버튼에 이벤트 연결하기
        ImageView bt_play = (ImageView)view.findViewById(R.id.bt_play);
        ImageView bt_stop = (ImageView)view.findViewById(R.id.bt_stop);

        bt_play.setOnClickListener(e->{
                Log.d(TAG,"파일명은" + music.getTitle());
                musicFragment.playMusic(music.getFile());
                Log.d(TAG,"파일은" + music.getFile());
                 map.put(music.getFile(),musicFragment.mediaPlayer);

        });

        bt_stop.setOnClickListener(e->{
            Log.d(TAG,"누름?");
//            mediaPlayer = map.get(music.getFile());
//            if(mediaPlayer != null) {
//                musicFragment.stopMusic(mediaPlayer);
//            }
            musicFragment.stopMusic(music.getFile());
        });

        return view;
    }
}