package com.example.firsttopic.GetSetfile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

//序号记录车辆号、充值金额、操作人、时间。
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public  static final String CREATE_CAR = "create table CAR("
            + "id integer primary key,"
            + "carid text,"
            + "manoy text,"
            + "username text,"
            + "time text)";

    private Context mcontext;
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_CAR);
        Toast.makeText(mcontext,"成功了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists CAR");
        Log.d("开始删除", "删除中 ");
        onCreate(db);
    }
}
