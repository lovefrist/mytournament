package com.example.firsttopic.Environmental;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

//序号记录车辆号、充值金额、操作人、时间。
public class MyfiveDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_CARFIVE = "create table setindex("
            +"id integer primary key autoincrement,"
            + "temperature integer,"
            + "humidity text,"
            + "LightIntensity text,"
            + "co2 text,"
            + "pm25 text,"
            +"data text,"
            + "status text)";

    private Context mcontext;

    public MyfiveDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CARFIVE);
        Toast.makeText(mcontext, "成功了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists setindex");
        Log.d("开始删除", "删除中 ");
        onCreate(db);
    }
}
