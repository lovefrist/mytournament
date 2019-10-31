package com.example.firsttopic.seventhtop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.fiveTesting.IndexActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class SeventhTopActivity extends MyAppCompatActivity {
    private Switch aSwitch;
    private TextView switchText;
    private List<EditText> edtextlist;
    private OkHttpClient okHttpClient;
    private String urljson = "http://192.168.3.5:8088/transportservice/action/GetAllSense.do";
    private String stajson = "http://192.168.3.5:8088/transportservice/action/GetRoadStatus.do";
    private Handler handler = new Handler();
    private List<String> applist, valuelist, mindexlist;
    private List<Integer> etlist;
    private boolean switchTesting = true;
    private static final int NOTIFICA_ID = 0x0001;
    private static final String CHANNEL_ID = "Exceed";
    private Button mbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh_top);
        Menu menu = super.setMenu(this, "阈值设置", null);
        View view = menu.getLinear_left();
        view.setBackgroundColor(Color.rgb(255, 255, 255));
        applist = new ArrayList<>();
        etlist = new ArrayList<>();
        valuelist = new ArrayList<>();
        valuelist.add("温度");
        valuelist.add("湿度");
        valuelist.add("光照");
        valuelist.add("PM2.5");
        valuelist.add("CO2");
        valuelist.add("道路状态");
        mindexlist = new ArrayList<>();
        mindexlist.add("temperature");
        mindexlist.add("humidity");
        mindexlist.add("LightIntensity");
        mindexlist.add("pm2.5");
        mindexlist.add("co2");
        mindexlist.add("Status");
        aSwitch = findViewById(R.id.st_switchid);
        switchText = findViewById(R.id.tv_switchteext);


        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                for (int i = 0; i < edtextlist.size(); i++) {
                    Log.d(TAG, "onCheckedChanged: 开始经行判断" + (edtextlist.get(i).getText().toString().equals("")));
                    if (edtextlist.get(i).getText().toString().equals("")) {
                        Toast.makeText(SeventhTopActivity.this, "第" + i + "个阀值没有设置 你是tm的hp？？", Toast.LENGTH_SHORT).show();

                        Log.d(TAG, "onCheckedChanged: 数据错误");
                    }
                }
                switchTesting = true;
                switchText.setText("开");
                mbutton.setEnabled(false);
                for (int i = 0; i < edtextlist.size(); i++) {
                    edtextlist.get(i).setFocusable(false);//设置表达不可编辑
                    edtextlist.get(i).setFocusableInTouchMode(false);//设置表达不可编辑

                }
                getjson();
            } else {
                switchText.setText("关");
                switchTesting = false;
                mbutton.setEnabled(true);
                for (int i = 0; i < edtextlist.size(); i++) {
                    edtextlist.get(i).setFocusable(true);
                    edtextlist.get(i).setFocusableInTouchMode(true);
                    edtextlist.get(i).requestFocus();//刷新控件

                }
            }
        });

        edtextlist = new ArrayList<>();
        edtextlist.add(findViewById(R.id.et_temperature));
        edtextlist.add(findViewById(R.id.et_humidity));
        edtextlist.add(findViewById(R.id.et_LightIntensity));
        edtextlist.add(findViewById(R.id.et_co2));
        edtextlist.add(findViewById(R.id.et_pm2_5));
        edtextlist.add(findViewById(R.id.et_status));

        mbutton = findViewById(R.id.btn_Preservation);
        mbutton.setOnClickListener(v -> {
            Log.d(TAG, "onClick: 开始存入数据");
            save();
        });
        setsave();
        aSwitch.setChecked(true);
    }

    public void getjson() {
        new Thread(() -> {
            while (switchTesting) {
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
                            Tastingindeex();
                        });
                        response.body().close();
                        map.clear();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis(); //开始
                if (end-startTime< 10000){
                    try {
                        Thread.sleep(10000-(end-startTime));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

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

            } else if (num == 1) {
                applist.add(jsonObject.getString("Status"));
                Log.d(TAG, "parseJSON: " + jsonObject.getString("Status"));
                Log.d(TAG, "parseJSON: " + applist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Tastingindeex() {
        for (int i = 0; i < applist.size(); i++) {
//         etlist.add(Integer.parseInt(edtextlist.get(i).getText().toString()));
            if (!edtextlist.get(i).getText().toString().equals("")){
                if (Integer.parseInt(applist.get(i)) > Integer.parseInt(edtextlist.get(i).getText().toString())) {
                    seetNotfi(valuelist.get(i), applist.get(i), i);
                }
            }

        }
        applist.clear();

    }

    private void seetNotfi(String index, String value, int ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(this, IndexActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//获取系统的NotificationManager服务
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "temperature", NotificationManager.IMPORTANCE_HIGH);//设置通知chanel的描述消息
            // 设置渠道描述
            channel.setDescription("测试通知组");
            // 是否绕过请勿打扰模式
            channel.canBypassDnd();
            // 设置绕过请勿打扰模式
            channel.setBypassDnd(true);
            // 桌面Launcher的消息角标
            channel.canShowBadge();
            // 设置显示桌面Launcher的消息角标
            channel.setShowBadge(true);
            // 设置通知出现时声音，默认通知是有声音的
            channel.setSound(null, null);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400,
                    300, 200, 400});
            manager.createNotificationChannel(channel);


            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setContentTitle("警告" + index + "!!!!!!!!!!!!!!!")
                    .setContentText(index + "过高" + "当前的" + index + "值" + value)
                    .setSmallIcon(R.mipmap.ico_skeleton)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ico_skeleton))
                    .setContentIntent(pendingIntent)
                    .build();
            manager.notify(ID, notification);
        } else {
            Intent intent = new Intent(this, IndexActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//获取系统的NotificationManager服务
            Notification notification = new NotificationCompat.Builder(this, "CHANNEL_ID")
                    .setAutoCancel(true)
                    .setContentTitle("警告" + index + "!!!!!!!!!!!!!!!")
                    .setContentText(index + "过高" + "当前的" + index + "值" + value)
                    .setSmallIcon(R.mipmap.ico_skeleton)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ico_skeleton))
                    .setContentIntent(pendingIntent)
                    .build();
            manager.notify(ID, notification);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        switchTesting = false;
    }

    private void save() {
        Log.d(TAG, "save: 进入写入文件方法");
        SharedPreferences.Editor editor = getSharedPreferences("indexdata", MODE_PRIVATE).edit();
        for (int i = 0; i < mindexlist.size(); i++) {
            editor.putString(mindexlist.get(i), edtextlist.get(i).getText().toString());
            Log.d(TAG, "save: " + mindexlist.size());
            Log.d(TAG, "save: " + edtextlist.get(i).getText().toString());
        }
        editor.apply();
    }

    private void setsave() {
        Log.d(TAG, "setsave: 进入读取文件的方法");
        SharedPreferences pref = getSharedPreferences("indexdata", MODE_PRIVATE);

        for (int i = 0; i < mindexlist.size(); i++) {
            edtextlist.get(i).setText(pref.getString(mindexlist.get(i), ""));
            Log.d(TAG, "setsave: 读取数据的内容" + pref.getString(mindexlist.get(i), ""));
        }
    }
}
