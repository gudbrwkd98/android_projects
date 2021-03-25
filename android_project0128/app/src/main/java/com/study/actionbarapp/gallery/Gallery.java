package com.study.actionbarapp.gallery;

import android.graphics.Bitmap;

public class Gallery {
    private int gallery_id;
    private String title;
    private String filename; //파일명...
    private Bitmap bitmap; //이미지 정보객체 (Image view 에 사용할 예정 )
    //추가할 예정 IMAGEview 는 스트링으로 대체불가ㅣ


    public int getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(int gallery_id) {
        this.gallery_id = gallery_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
