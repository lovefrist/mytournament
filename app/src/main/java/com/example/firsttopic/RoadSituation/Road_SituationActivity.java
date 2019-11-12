package com.example.firsttopic.RoadSituation;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
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

public class Road_SituationActivity extends MyAppCompatActivity {
    private TextView tvXueyuanRoad, tvlenovoroad, tvhospitalroad, tvhappyroad, tvparkinglot,tvtemperature,tvhumidity,tvparticulate,tvtime,tvxingqi;
    private List<TextView> listring = new ArrayList();
    private List<TextView> listhuantoad = new ArrayList();
    private OkHttpClient okHttpClient;
    private String urlstr = "http://192.168.3.5:8088/transportservice/action/GetRoadStatus.do";
    private Handler handler = new Handler();
    private List<TextView> listnum = new ArrayList();
    private ImageView imgar, imbay, imrotate;
    private ProgressDialog   progressDialog;
    private String urlrot = "http://192.168.3.5:8088/transportservice/action/GetAllSense.do";
    private boolean  swkai = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Menu menu = super.setMenu(this, "道路查询", null);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
        setContentView(R.layout.activity_road__situation);
        tvXueyuanRoad = findViewById(R.id.tv_XueyuanRoad);
        tvlenovoroad = findViewById(R.id.tv_lenovoroad);
        tvhospitalroad = findViewById(R.id.tv_hospitalroad);
        tvhappyroad = findViewById(R.id.tv_happyroad);
        listring.add(findViewById(R.id.tv_ring_expressway1));
        listring.add(findViewById(R.id.tv_ring_expressway2));
        listring.add(findViewById(R.id.tv_ring_expressway3));
        listring.add(findViewById(R.id.tv_ring_expressway4));
        listring.add(findViewById(R.id.tv_ring_expressway5));
        listring.add(findViewById(R.id.tv_ring_expressway6));
        listring.add(findViewById(R.id.tv_ring_expressway7));
        listring.add(findViewById(R.id.tv_ring_expressway8));
        listhuantoad.add(findViewById(R.id.tv_ringspeed1));
        listhuantoad.add(findViewById(R.id.tv_ringspeed2));
        tvparkinglot = findViewById(R.id.tv_parkinglot);
        listnum.add(tvXueyuanRoad);
        listnum.add(tvlenovoroad);
        listnum.add(tvhospitalroad);
        listnum.add(tvhappyroad);
        listnum.add(tvparkinglot);
        imgar = findViewById(R.id.im_gar);
        imbay = findViewById(R.id.im_bay);
        imrotate = findViewById(R.id.im_rotate);
        tvtemperature = findViewById(R.id.tv_temperature13);
        tvhumidity = findViewById(R.id.tv_humidity13);
        tvparticulate = findViewById(R.id.tv_particulate13);
        tvtime = findViewById(R.id.tv_time);
        tvxingqi = findViewById(R.id.tv_xingqi);
        imrotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setJson();
            }
        });
        getdataJson();
        ChargePolice();
    }

    private void setJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(Road_SituationActivity.this);
                        progressDialog.setMessage("加载中");
                        progressDialog.show();
                    }
                });
                try {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");
                    Map map = new HashMap();
//        {"UserName":"user1"}
                    map.put("UserName", "user1");
                    String parms = gson.toJson(map);
                    RequestBody requestBody = RequestBody.create(mediaType, parms);
                    Request request = new Request.Builder().post(requestBody).url(urlrot).build();
                    okHttpClient = new OkHttpClient();
                    Response response = okHttpClient.newCall(request).execute();
                    String data = response.body().string();
                    Date date1 = new Date();
                    Log.d(TAG, "run: 当前时间"+date1);
                    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat week = new SimpleDateFormat("EEEE");
                    String timech = time.format(date1);
                    String weekch = week.format(date1);
                    JSONObject jsonObject = new JSONObject(data);
                    int temperature = jsonObject.getInt("temperature");
                    int humidity = jsonObject.getInt("humidity");
                    int PM25 = jsonObject.getInt("pm2.5");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            tvtemperature.setText("温度："+temperature+"℃");
                            tvhumidity.setText("相对湿度："+humidity+"%");
                            tvparticulate.setText("PM2.5："+PM25+"µg/m3");
                            tvtime.setText(timech);
                            tvxingqi.setText(weekch);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    private void getdataJson() {
        new Thread(() -> {
            while (swkai) {
                long startTime = System.currentTimeMillis(); //开始
                try {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");
                    Map map = null;
                    for (int i = 1; i <= 7; i++) {
                        map = new HashMap<>();
                        map.put("RoadId", i);
                        map.put("UserName", "user1");

                        String parms = gson.toJson(map);
                        RequestBody requestBody = RequestBody.create(mediaType, parms);
                        Request request = new Request.Builder().post(requestBody).url(urlstr).build();
                        okHttpClient = new OkHttpClient();
                        Response response = okHttpClient.newCall(request).execute();
                        String resuit = response.body().string();
                        JSONObject jsonObject = new JSONObject(resuit);
                        int Status = jsonObject.getInt("Status");
                        int find = Status;
                        int idn = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (idn == 5 || idn == 6) {
                                    ChargeColer(idn, find);
                                } else {

                                    if (idn == 7) {
                                        ChangeColor(5, find);
                                    } else {
                                        ChangeColor(idn, find);
                                    }

                                }

                            }
                        });
                    }
                    long endtime = System.currentTimeMillis();
                    if (endtime - startTime >= 3000) {

                    } else {
                        Thread.sleep(3000 - (endtime - startTime));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        swkai= false;
    }

    private void ChangeColor(int i, int find) {

        switch (find) {
            case 1:
                listnum.get(i - 1).setBackgroundColor(Color.rgb(106, 184, 46));
                break;
            case 2:
                listnum.get(i - 1).setBackgroundColor(Color.rgb(236, 233, 58));
                break;
            case 3:
                listnum.get(i - 1).setBackgroundColor(Color.rgb(244, 155, 37));
                break;
            case 4:
                listnum.get(i - 1).setBackgroundColor(Color.rgb(227, 53, 50));
                break;
            case 5:
                listnum.get(i - 1).setBackgroundColor(Color.rgb(176, 30, 35));
                break;

        }


    }

    private void ChargeColer(int id, int find) {
        switch (id) {
            case 5:
                for (int i = 0; i < listring.size(); i++) {
                    switch (find) {
                        case 1:
                            listring.get(i).setBackgroundColor(Color.rgb(106, 184, 46));
                            break;
                        case 2:
                            listring.get(i).setBackgroundColor(Color.rgb(236, 233, 58));
                            break;
                        case 3:
                            listring.get(i).setBackgroundColor(Color.rgb(244, 155, 37));
                            break;
                        case 4:
                            listring.get(i).setBackgroundColor(Color.rgb(227, 53, 50));
                            break;
                        case 5:
                            listring.get(i).setBackgroundColor(Color.rgb(176, 30, 35));
                            break;
                    }
                }
                break;
            case 6:
                for (int i = 0; i < listhuantoad.size(); i++) {
                    switch (find) {
                        case 1:
                            listhuantoad.get(i).setBackgroundColor(Color.rgb(106, 184, 46));
                            break;
                        case 2:
                            listhuantoad.get(i).setBackgroundColor(Color.rgb(236, 233, 58));
                            break;
                        case 3:
                            listhuantoad.get(i).setBackgroundColor(Color.rgb(244, 155, 37));
                            break;
                        case 4:
                            listhuantoad.get(i).setBackgroundColor(Color.rgb(227, 53, 50));
                            break;
                        case 5:
                            listhuantoad.get(i).setBackgroundColor(Color.rgb(176, 30, 35));
                            break;
                    }
                }
                break;
        }
    }

    private void ChargePolice() {
        new Thread(() -> {
            int i = 1;
            while (true) {
                int find = i;
                handler.post(() -> {
                    if (find % 2 != 0) {
                        imgar.setImageResource(R.drawable.jiaojing2_2);
                        imbay.setImageResource(R.drawable.jiaojing1_2);
                    } else {
                        imgar.setImageResource(R.drawable.jiaojing2_1);
                        imbay.setImageResource(R.drawable.jiaojing1_1);
                    }
                });

                i++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
