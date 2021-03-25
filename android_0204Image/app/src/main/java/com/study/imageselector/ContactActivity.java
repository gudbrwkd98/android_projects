package com.study.imageselector;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

public class ContactActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    public void openContact(View view){
        //주소록 앱은 우리의 앱과 다른 별도의 앱이므로
        //컨텐트 프로바이더 얻기..

        Cursor cursor= this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);

        while(cursor.moveToNext()){
            Log.d(TAG,"전화번호 =" + cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
    }
}