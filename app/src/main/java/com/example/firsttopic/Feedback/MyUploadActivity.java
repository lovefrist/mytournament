package com.example.firsttopic.Feedback;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUploadActivity extends MyAppCompatActivity {
    private RecyclerView recyclerView;
    private MyopinionDatabaseHelper databaseHelper = new MyopinionDatabaseHelper(this,"opinion",null,1);
    private List<Map> mapList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setMenu(this,"我的意见",null);
        setContentView(R.layout.activity_myupload);
        recyclerView = findViewById(R.id.rv_opinion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       SQLiteDatabase database =  databaseHelper.getWritableDatabase();
     Cursor cursor = database.query("indextall",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Map map1 = new HashMap();
              String title = cursor.getString(cursor.getColumnIndex("title"));
                map1.put("title",title);
              String time = cursor.getString(cursor.getColumnIndex("time"));
                map1.put("time",time);
                mapList.add(map1);
            }while (cursor.moveToNext());
        }
        Log.d(TAG, "onCreate: 大小 "+mapList.size());
        MyUploadAdapter adapter = new MyUploadAdapter(this,mapList);
        recyclerView.setAdapter(adapter);
    }
}
