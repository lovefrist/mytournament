package com.example.firsttopic.fiveTesting;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IndexActivity extends MyAppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> textlist;
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler();
    private String urljson = "http://192.168.3.5:8088/transportservice/action/GetAllSense.do";
    private String stajson = "http://192.168.3.5:8088/transportservice/action/GetRoadStatus.do";
    private List<String> applist;
    private IndexAdapter indexAdapter;
    private String resuitsta;
    private List<getindexapp> sralist;
    private MyfiveDatabaseHelper dbHelper;
    private boolean kaiguan = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        dbHelper = new MyfiveDatabaseHelper(this, "indexsetlis", null, 9);
        Menu menu = super.setMenu(this, "环境指标", null);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
//        空气温度、空气湿度、PM2.5、CO2、光照、道路状态
        textlist = new ArrayList<>();
        applist = new ArrayList<>();
        sralist = new ArrayList<>();
        textlist.add("温度");
        textlist.add("湿度");
        textlist.add("光照");
        textlist.add("CO2");
        textlist.add("PM2.5");
        textlist.add("道路状态");
        getjson();
        recyclerView = findViewById(R.id.rv_indextext);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new MyDecorationhor());
    }

    public class MyDecorationhor extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(2, 2, 2, 2);
        }
    }

    public void getjson() {
        new Thread(() -> {
            while (kaiguan) {
                long startTime = System.currentTimeMillis(); //开始
                try {


                    String url;
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    Gson gson1 = new Gson();
                    MediaType mediaType = MediaType.parse("application/json");
                    Map map = new HashMap<>();
                    for (int i = 0; i < 2; i++) {
                        if (i == 0) {
                            url = urljson;
                            map.put("UserName", "user1");
                        } else {
                            url = stajson;
                            map.put("RoadId", 1);
                            map.put("UserName", "user1");
                        }
                        String parms = gson.toJson(map);
                        RequestBody requestBody = RequestBody.create(mediaType, parms);
                        Request request = new Request.Builder().post(requestBody).url(url).build();
                        okHttpClient = new OkHttpClient();
                        Response response = okHttpClient.newCall(request).execute();
                        String resuit = response.body().string();
                        int finalI = i;
                        handler.post(() -> {
                            parseJSON(resuit, finalI);
                            if (finalI == 1) {
                                AddsqlData(applist);
                                indexAdapter = new IndexAdapter(IndexActivity.this, textlist, applist);
                                recyclerView.setAdapter(indexAdapter);

                            }
                        });
                        response.body().close();
                        map.clear();
                    }
                    long stopTime = System.currentTimeMillis(); //结束
                    if (stopTime-startTime<3000){
                        Thread.sleep(3000-(stopTime-startTime));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                applist.clear();
            }
        }).start();
    }

    private void parseJSON(String jsonData, int num) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            Log.d("tianj", "-----------------------------------------------------------------------------------" + num);

            if (num == 0) {
                Log.d(TAG, "parseJSON: " + jsonData);
                Log.d(TAG, "parseJSON: " + jsonObject.getString("pm2.5"));
                Log.d(TAG, "parseJSON: " + jsonObject.getString("co2"));
                Log.d(TAG, "parseJSON: " + jsonObject.getString("LightIntensity"));
                Log.d(TAG, "parseJSON: " + jsonObject.getString("humidity"));
                Log.d(TAG, "parseJSON: " + jsonObject.getString("temperature"));
                applist.add(jsonObject.getString("pm2.5"));
                applist.add(jsonObject.getString("co2"));
                applist.add(jsonObject.getString("LightIntensity"));
                applist.add(jsonObject.getString("humidity"));
                applist.add(jsonObject.getString("temperature"));
                Log.d(TAG, "parseJSON: " + applist);

            } else if (num == 1) {
                applist.add(jsonObject.getString("Status"));
                Log.d(TAG, "parseJSON: " + jsonObject.getString("Status"));
                Log.d(TAG, "parseJSON: " + applist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AddsqlData(List<String> list) {
        SQLiteDatabase adddata = dbHelper.getWritableDatabase();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String dateNowStr = sdf.format(date);
        ContentValues values = new ContentValues();
        values.put("temperature", list.get(0));
        values.put("humidity", list.get(1));
        values.put("LightIntensity", list.get(2));
        values.put("co2", list.get(3));
        values.put("pm25", list.get(4));
        values.put("status", list.get(5));
        values.put("data",dateNowStr);
        Log.d(TAG, "AddsqlData: 存入文件的内容" + values);
        long num =  adddata.insert("setindex", null, values);
        Cursor cursor = adddata.query("setindex",null,null,null,null,null,null);
        if (num>20){
            adddata.execSQL("delete from setindex where id = (select id from setindex limit 1)");
        }
        Log.d(TAG, "AddsqlData: "+num+"/t"+cursor.getCount());
        values.clear();
//        try {
//            Thread.sleep(5000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        kaiguan = false;
        Log.d(TAG, "onDestroy: 结束线程");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        kaiguan = true;
        getjson();
        Log.d(TAG, "onDestroy: 回复线程");
    }
}
