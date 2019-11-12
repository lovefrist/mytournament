package com.example.firsttopic.leftIndex;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LeftIndexActivity extends MyAppCompatActivity {
    private RecyclerView recyclerView;
    private String urlindex = "http://192.168.3.5:8088/transportservice/action/GetAllSense.do";
    private List<Integer> listimgsrc = new ArrayList();
    private List<String> listindex = new ArrayList();
    private List<Integer> listnum;
    private Handler handler = new Handler();
    private MyindexDatabaseHelper databaseHelper = new MyindexDatabaseHelper(this,"index5",null,1);
    private boolean swtkai = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Menu menu = super.setMenu(this, "生活指数", null);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
        setContentView(R.layout.activity_left_index);
        setdatalist();
        recyclerView = findViewById(R.id.rv_indextfive);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));


        getIndexdata();
    }

    private void getIndexdata() {

        new Thread(() -> {
            try {
                while (swtkai) {
                    long startime = System.currentTimeMillis();
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");
                    Map map = new HashMap();
                    map.put("UserName", "user1");
                    String params = gson.toJson(map);
                    RequestBody requestBody = RequestBody.create(mediaType, params);
                    Request request = new Request.Builder().post(requestBody).url(urlindex).build();
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Response response = okHttpClient.newCall(request).execute();
                    String index = response.body().string();
                    JSONObject jsonObject = new JSONObject(index);
                    int LightIntensity = jsonObject.getInt("LightIntensity");
                    int temperature = jsonObject.getInt("temperature");
                    int co2 = jsonObject.getInt("co2");
                    int pm25 = jsonObject.getInt("pm2.5");
                    listnum = new ArrayList<>();
                    listnum.add(LightIntensity);
                    listnum.add(temperature);
                    listnum.add(temperature);
                    listnum.add(co2);
                    listnum.add(pm25);
                  SQLiteDatabase database = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("LightIntensity",listnum.get(0));
                    values.put("temperature",listnum.get(1));
                    values.put("co2",listnum.get(2));
                    values.put("pm25",listnum.get(3));
                   long num =  database.insert("indextall",null,values);
                    Log.d(TAG, "getIndexdata: 存入成功");
                    if (num>20){
                        database.execSQL("delete from indextall where id = (select id from indextall limit 1)");
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                           indexAdapter adapter = new indexAdapter(LeftIndexActivity.this, listimgsrc, listindex, listnum);
                            recyclerView.setAdapter(adapter);

                        }
                    });


                    long endtime = System.currentTimeMillis();
                    if ((endtime - startime) < 3000) {
                        Thread.sleep(3000 - (endtime - startime));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        swtkai = false;
    }

    private void setdatalist() {
        listimgsrc.add(R.drawable.icon101);
        listimgsrc.add(R.drawable.icon102);
        listimgsrc.add(R.drawable.icon103);
        listimgsrc.add(R.drawable.icon104);
        listimgsrc.add(R.drawable.icon105);
        listindex.add("紫外线指数");
        listindex.add("温度指数");
        listindex.add("穿衣指数");
        listindex.add("运动指数");
        listindex.add("空气污染扩散指数");
    }
}
