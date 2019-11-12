package com.example.firsttopic.leftIndex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

//序号记录车辆号、充值金额、操作人、时间。
public class MyindexDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_CARFIVE = "create table indextall("
            +"id integer primary key autoincrement,"
            + "LightIntensity integer,"
            + "temperature text,"
            + "co2 text,"
            + "pm25 text)";


    private Context mcontext;

    public MyindexDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CARFIVE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists setindex");
        Log.d("开始删除", "删除中 ");
        onCreate(db);
    }
}
