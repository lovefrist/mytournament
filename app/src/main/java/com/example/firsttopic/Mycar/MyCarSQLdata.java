package com.example.firsttopic.Mycar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyCarSQLdata extends SQLiteOpenHelper {
    public static final String CAR_DATA = "create table CARRecharge("
            + "id integer primary key autoincrement,"
            + "carid text,"
            + "money text,"
            + "timedata text)";
    private Context context;

    public MyCarSQLdata(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CAR_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists CARRecharge");
        onCreate(db);
    }
}
