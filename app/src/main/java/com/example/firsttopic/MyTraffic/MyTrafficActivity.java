package com.example.firsttopic.MyTraffic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.firsttopic.Environmental.IndexActivity;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.util.Toastutil;
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

public class MyTrafficActivity extends MyAppCompatActivity {
    private ViewPager viewPager;
    private List<View> list = new ArrayList();
    private List<TextView> listtext = new ArrayList();
    private String nowstring = "http://192.168.3.5:8088/transportservice/action/GetTrafficLightNowStatus.do";
    private String Configuri = "http://192.168.3.5:8088/transportservice/action/GetTrafficLightConfigAction.do";
    private String setnewUri = "http://192.168.3.5:8088/transportservice/action/SetTrafficLightNowStatus.do";
    private String setlempnewUri = "http://192.168.3.5:8088/transportservice/action/SetTrafficLightConfig.do";
    private String getallindex = "http://192.168.3.5:8088/transportservice/action/GetAllSense.do";
    private String GetLightSense = "http://192.168.3.5:8088/transportservice/action/GetLightSenseValve.do";
    private String getStatus = "http://192.168.3.5:8088/transportservice/action/GetRoadStatus.do";
    private OkHttpClient okHttpClient;
    private List<Map> listcpnfig = new ArrayList<>();
    private List<Map> liststatehen = new ArrayList<>();
    private List<Map> liststatezhong = new ArrayList<>();
    private Handler handler = new Handler();
    private lemptraAdapter adapter;
    private android.app.AlertDialog alertDialog;
    private static final String CHANNEL_ID = "Exceed";

    private boolean skydata = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_traffic);
        viewPager = findViewById(R.id.vp_road);
        list.add(LayoutInflater.from(this).inflate(R.layout.layout_roadone, null));
        list.add(LayoutInflater.from(this).inflate(R.layout.layout_roadtwo, null));
        RoadTraffocAdapter adapter = new RoadTraffocAdapter(list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // 给viewPager添加监听事件
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ChengeColor(position);
                if (position == 0) {
                    setViewAdapter();
                } else {
                    setViewset();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        listtext.add(findViewById(R.id.tv_mytraffic));
        listtext.add(findViewById(R.id.tv_roadenvironment));
        for (int i = 0; i < listtext.size(); i++) {
            int find = i;
            listtext.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(find);//设置viewPager的页面位置
                }
            });
        }
        setViewAdapter();
        gettongzhi();
    }

    private void ChengeColor(int position) {
        for (int i = 0; i < listtext.size(); i++) {
            if (i == position) {
                listtext.get(position).setTextColor(Color.rgb(0, 0, 0));
            } else {
                listtext.get(i).setTextColor(Color.rgb(204, 204, 204));
            }
        }
    }

    private void setViewAdapter() {
        RecyclerView recyclerView = list.get(0).findViewById(R.id.tv_lempconfigure);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyTrafficActivity.this));
        adapter = new lemptraAdapter(MyTrafficActivity.this, listcpnfig, liststatehen, liststatezhong);
        recyclerView.setAdapter(adapter);
        listcpnfig.clear();
        liststatehen.clear();
        liststatehen.clear();
        getDataJson();
        lemptraAdapter.onclk(new ButtonDlong() {
            @Override
            public void onClick(int poast, String str) {
                Dlongoncli(poast, str);
            }
        });
    }

    private void setViewset() {
        TextView tvrealnum = list.get(1).findViewById(R.id.tv_realnum);
        TextView tvPm25index = list.get(1).findViewById(R.id.tv_Pm25index);
        VideoView videoview = list.get(1).findViewById(R.id.video_view);
        TextView tvnotnight = list.get(1).findViewById(R.id.tv_notnight);
        TextView tvtherhoodtmax = list.get(1).findViewById(R.id.tv_therhoodtmax);
        TextView tvherhoodtmin = list.get(1).findViewById(R.id.tv_herhoodtmin);
        TextView threshold001 = list.get(1).findViewById(R.id.threshold001);
        TextView tvthreshold002 = list.get(1).findViewById(R.id.tv_threshold002);
        TextView tvthreshold003 = list.get(1).findViewById(R.id.tv_threshold003);
        TextView tvLightindex = list.get(1).findViewById(R.id.tv_Lightindex);
        View viewthreshold1 = list.get(1).findViewById(R.id.view_threshold1);
        View viewthreshold2 = list.get(1).findViewById(R.id.view_threshold2);
        View viewthreshold3 = list.get(1).findViewById(R.id.view_threshold3);
        View viewindex1 = list.get(1).findViewById(R.id.view_index1);
        View viewindex2 = list.get(1).findViewById(R.id.view_index2);
        TextView tvindex001 = list.get(1).findViewById(R.id.tv_index001);
        TextView tvindex002 = list.get(1).findViewById(R.id.tv_index002);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f);
//////        WRAP_CONTENT 铺满整个屏幕
//////        MATCH_PARENT 不改变原来的大小
////        viewthreshold1.setLayoutParams(lp);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int LightIntensity = 0;
                    while (skydata){
                            long starytime = System.currentTimeMillis();
                    for (int i = 0; i < 2; i++) {
                        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                        MediaType mediaType = MediaType.parse("application/json");
                        Map map = new HashMap();
                        map.put("UserName", "user1");
                        String parms = gson.toJson(map);
                        RequestBody requestBody = RequestBody.create(mediaType, parms);
                        Request request;
                        if (i == 0) {
                            request = new Request.Builder().post(requestBody).url(getallindex).build();
                        } else {
                            request = new Request.Builder().post(requestBody).url(GetLightSense).build();
                        }

                        okHttpClient = new OkHttpClient();
                        Response response = okHttpClient.newCall(request).execute();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (i == 0) {
                            int pm25 = jsonObject.getInt("pm2.5");
                            LightIntensity = jsonObject.getInt("LightIntensity");
                            int finalLightIntensity = LightIntensity;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tvrealnum.setText(pm25 + "");
                                    if (pm25 > 200) {
                                        tvPm25index.setText("不适合出行");
                                        videoview.setVisibility(View.GONE);
                                    } else {
                                        tvPm25index.setText("适合出行");
                                        videoview.setVisibility(View.VISIBLE);
                                        videoview.setVideoURI(Uri.parse("android.resource://com.example.firsttopic/"+R.raw.mp401));
                                        videoview.start();
                                    }
                                    tvnotnight.setText(finalLightIntensity + "");
                                    tvindex002.setText(finalLightIntensity + "");

                                }
                            });
                        } else {
                            String Down = jsonObject.getString("Down");//最高值
                            float min = Integer.parseInt(Down);
                            String Up = jsonObject.getString("Up");//最低值
                            float max = Integer.parseInt(Up);
                            float finalLightIntensity1 = LightIntensity;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (finalLightIntensity1>max){
                                        tvLightindex.setText("光照较强，出行请戴墨镜");
                                    }else if (finalLightIntensity1<min){
                                        tvLightindex.setText("光照较弱，出行请开灯");
                                    }else {
                                        tvLightindex.setText("光照良好，正常出行，注意安全");
                                    }
                                    tvtherhoodtmax.setText(Up);
                                    tvherhoodtmin.setText(Down);
                                    tvthreshold002.setText(Down);
                                    tvthreshold003.setText(Up);
                                    float quan1 = min/(max + min);
                                    float quan2 = max / (max + min);
                                    float quan3 = finalLightIntensity1 / (max + min);
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, quan1);
                                    viewthreshold1.setLayoutParams(lp);
                                    threshold001.setLayoutParams(lp);
                                    viewthreshold3.setLayoutParams(lp);
                                    tvthreshold003.setLayoutParams(lp);
                                    LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, quan3);
                                    viewindex1.setLayoutParams(lp3);
                                    tvindex001.setLayoutParams(lp3);
//                                    LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, (1 - quan3) * 2);
//                                    viewindex2.setLayoutParams(lp4);
//                                    tvindex002.setLayoutParams(lp4);
                                }
                            });
                        }
                    }
                        long endtime = System.currentTimeMillis();
                    if ((endtime-starytime)<3000){
                        Thread.sleep(3000-(endtime-starytime));
                    }
                    }
                } catch (IOException e) {
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void Dlongoncli(int poast, String str) {
        List<EditText> listtext = new ArrayList();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_lempoutbes, null);
        listtext.add(view.findViewById(R.id.et_redlemp));
        listtext.add(view.findViewById(R.id.et_yellowlemp));
        listtext.add(view.findViewById(R.id.et_greenlemp));
        Button buttonquren = view.findViewById(R.id.btn_setlemp);
        Button buttondeldet = view.findViewById(R.id.btn_setlempdeld);
        for (int i = 0; i < listtext.size(); i++) {
            int find = i;
            listtext.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (start == 0 && s.toString().equals("0") && count == 1) {
                        //输入的第一个字符为"."
                        listtext.get(find).setText("");
                    } else if (s.length() > 2 && count != 0) {
                        //当整数位数输入到达被要求的上限,并且当前在输入字符,而不是减少字符
                        listtext.get(find).setText(s.subSequence(0, s.toString().length() - 1));
                        listtext.get(find).setSelection(s.toString().length() - 1);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!listtext.get(find).getText().toString().equals("")) {
                        if (Integer.parseInt(listtext.get(find).getText().toString()) > 30) {
                            listtext.get(find).setText("30");
                        }
                    }
                }
            });
        }

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(view);
        dialog.show();
        buttonquren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean seyky = true;
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < listtext.size(); i++) {
                    if (listtext.get(i).getText().toString().equals("")) {
                        seyky = false;
                        break;
                    }
                    list.add(Integer.parseInt(listtext.get(i).getText().toString()));
                }
                if (seyky) {
                    setDataJson(poast, list, str);
                    dialog.dismiss();
                } else {
                    Toastutil.showmes(MyTrafficActivity.this, "输入不能为空");
                    list.clear();
                }

            }
        });
        buttondeldet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);

    }

    private void getDataJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        View view = LayoutInflater.from(MyTrafficActivity.this).inflate(R.layout.changpartext_layout, null);
                        alertDialog = new android.app.AlertDialog.Builder(MyTrafficActivity.this).create();
                        alertDialog.setView(view);
                        alertDialog.show();
                        final Window window = alertDialog.getWindow();
                        window.setBackgroundDrawable(new ColorDrawable(0));
                        alertDialog.setCanceledOnTouchOutside(false);
                    }
                });
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                MediaType mediaType = MediaType.parse("application/json");
                for (int j = 0; j < 3; j++) {
                    String uri = null;
                    for (int i = 1; i < 6; i++) {
                        try {
                            Map map = new HashMap<>();
//       {"TrafficLightId":"1-1","UserName":"user1"}
//       {"TrafficLightId":"1","UserName":"user1"}
                            if (j == 0) {
                                uri = Configuri;
                                map.put("TrafficLightId", i + "");
                                map.put("UserName", "user1");
                            } else {
                                uri = nowstring;
                                map.put("TrafficLightId", i + "-" + j);
                                map.put("UserName", "user1");
                            }

                            String parms = gson.toJson(map);
                            RequestBody requestBody = RequestBody.create(mediaType, parms);
                            Request request = new Request.Builder().post(requestBody).url(uri).build();
                            okHttpClient = new OkHttpClient();
                            Response response = okHttpClient.newCall(request).execute();
                            String resuit = response.body().string();
                            JSONObject jsonObject = new JSONObject(resuit);
                            if (j == 0) {
                                Map map1 = new HashMap();
                                String GreenTime = jsonObject.getString("GreenTime");
                                String YellowTime = jsonObject.getString("YellowTime");
                                String RedTime = jsonObject.getString("RedTime");
                                map1.put("GreenTime", GreenTime);
                                map1.put("YellowTime", YellowTime);
                                map1.put("RedTime", RedTime);
                                listcpnfig.add(map1);
                            } else if (j == 1) {
                                Map map1 = new HashMap();
                                String Status = jsonObject.getString("Status");
                                int Time = jsonObject.getInt("Time");
                                map1.put("Status", Status);
                                map1.put("Time", Time);
                                liststatehen.add(map1);
                            } else {
                                Map map1 = new HashMap();
                                String Status = jsonObject.getString("Status");
                                int Time = jsonObject.getInt("Time");
                                map1.put("Status", Status);
                                map1.put("Time", Time);
                                liststatezhong.add(map1);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                        Toastutil.showmes(MyTrafficActivity.this, "刷新成功");
                    }
                });
            }
        }).start();

    }

    private void setDataJson(int poast, List<Integer> listcs, String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> list = new ArrayList();
                    list.add("RedTime");
                    list.add("YellowTime");
                    list.add("GreenTime");
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");

                    Map map = new HashMap();

                    map.put("TrafficLightId", poast);
                    for (int j = 0; j < listcs.size(); j++) {
                        map.put(list.get(j), listcs.get(j));
                    }
                    map.put("UserName", "user1");

                    String parms = gson.toJson(map);
                    RequestBody requestBody = RequestBody.create(mediaType, parms);
                    Request request;
                    request = new Request.Builder().post(requestBody).url(setlempnewUri).build();
                    okHttpClient = new OkHttpClient();
                    Response response = okHttpClient.newCall(request).execute();
                    String resuit = response.body().string();
                    JSONObject jsonObject = new JSONObject(resuit);

                    String RESULT = jsonObject.getString("RESULT");
                    if (RESULT.equals("S")) {
                        Toastutil.showmes(MyTrafficActivity.this, "路口" + poast + "配置成功");
                    } else {
                        Toastutil.showmes(MyTrafficActivity.this, "路口" + poast + "配置失败");
                        Dlongoncli(poast, str);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void gettongzhi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (skydata) {
                        long startime = System.currentTimeMillis();
                        for (int i = 1; i <= 12; i++) {
                            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                            MediaType mediaType = MediaType.parse("application/json");
                            Map map = new HashMap();
                            map.put("RoadId", i);
                            map.put("UserName", "user1");
                            String parms = gson.toJson(map);
                            RequestBody requestBody = RequestBody.create(mediaType, parms);
                            Request request;
                            request = new Request.Builder().post(requestBody).url(getStatus).build();
                            okHttpClient = new OkHttpClient();
                            Response response = okHttpClient.newCall(request).execute();
                            String resuit = response.body().string();
                            JSONObject jsonObject = new JSONObject(resuit);
                            int Status = jsonObject.getInt("Status");
                            if (Status > 3) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    Intent intent = new Intent(MyTrafficActivity.this, IndexActivity.class);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(MyTrafficActivity.this, 0, intent, 0);
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

                                    Notification notification = new Notification.Builder(MyTrafficActivity.this, CHANNEL_ID)
                                            .setAutoCancel(true)
//                                x号路口处于拥挤堵塞状态，请选择合适的路线”
                                            .setContentTitle("警告!!!!!!!!!!!!!!!")
                                            .setContentText(i + "号路口处于拥挤堵塞状态，请选择合适的路线")
                                            .setSmallIcon(R.mipmap.ico_skeleton)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ico_skeleton))
                                            .setContentIntent(pendingIntent)
                                            .build();
                                    manager.notify(i, notification);
                                } else {
                                    Intent intent = new Intent(MyTrafficActivity.this, MyTrafficActivity.class);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(MyTrafficActivity.this, 0, intent, 0);
                                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//获取系统的NotificationManager服务
                                    Notification notification = new NotificationCompat.Builder(MyTrafficActivity.this, "CHANNEL_ID")
                                            .setAutoCancel(true)
                                            .setContentTitle("警告!!!!!!!!!!!!!!!")
                                            .setContentText(i + "号路口处于拥挤堵塞状态，请选择合适的路线")
                                            .setSmallIcon(R.mipmap.ico_skeleton)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ico_skeleton))
                                            .setContentIntent(pendingIntent)
                                            .build();
                                    manager.notify(i, notification);//ID的名字唯一
                                }
                            }
                        }
                        long endtime = System.currentTimeMillis();
                        if ((endtime-startime)<5000){
                            Thread.sleep(5000-(endtime-startime));
                        }
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
        skydata = false;
    }
}
