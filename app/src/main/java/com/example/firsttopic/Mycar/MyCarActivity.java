package com.example.firsttopic.Mycar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.util.Toastutil;
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

public class MyCarActivity extends MyAppCompatActivity {
    private ViewPager viewPager;
    private List<TextView> textlist = new ArrayList<>();
    private List<View> list = new ArrayList<>();
    private String urljson = "http://192.168.3.5:8088/transportservice/action/GetCarAccountBalance.do";
    private String urlset = "http://192.168.3.5:8088/transportservice/action/SetCarAccountRecharge.do";
    private ReminderAdapter adapter;
    private List listdatamoney = new ArrayList();
    private Handler handler = new Handler();
    private boolean keyfist1 = true;
    private TextView usertext;
    private OkHttpClient okHttpClient;
    private ControlreAdapter controlreAdapter;
    private RechargeAdapteer rechargeAdapteer;
    private MyCarSQLdata sqLdata = new MyCarSQLdata(this, "CARrech", null, 1);
    private Thread thread;
     private boolean RUN= true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Menu menu = super.setMenu(this, "我的座驾", R.layout.layout_username);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
        try {
            View view = menu.getLinear_main_more();
            usertext = view.findViewById(R.id.tv_username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        menu.needmove = true;
        setContentView(R.layout.activity_my_car);
        setViewList();
        viewPager = findViewById(R.id.vp_mycar);
        MyCarvpAdapter adapter = new MyCarvpAdapter(list);
        viewPager.setAdapter(adapter);
        ChcengetextColor(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ChcengetextColor(position);
                if (position==0){
                    RUN = true;
                    getDataJson();
                }else {
                    RUN = false;
                    thread.interrupt();

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < textlist.size(); i++) {
            int find = i;
            textlist.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChcengetextColor(find);
                }
            });
        }

        getDataJson();
    }

    private void setViewList() {
        list.add(LayoutInflater.from(this).inflate(R.layout.layout_mycar01, null));
        list.add(LayoutInflater.from(this).inflate(R.layout.layout_mycar02, null));
        list.add(LayoutInflater.from(this).inflate(R.layout.layout_mycar03, null));
        textlist.add(findViewById(R.id.tv_mybalance));
        textlist.add(findViewById(R.id.tv_control));
        textlist.add(findViewById(R.id.tv_recharge));
        listdatamoney.add(0);
        listdatamoney.add(0);
        listdatamoney.add(0);
        listdatamoney.add(0);
    }

    private void ChcengetextColor(int position) {
        setViewAdapter(position);
        for (int i = 0; i < textlist.size(); i++) {
            if (i == position) {
                textlist.get(i).setTextColor(Color.rgb(255, 182, 193));
                viewPager.setCurrentItem(position);
            } else {
                textlist.get(i).setTextColor(Color.rgb(0, 0, 0));
            }
        }
    }

    private void setViewAdapter(int position) {
        switch (position) {
            case 0:
                adapter = new ReminderAdapter(this, listdatamoney, new MyOnclick() {
                    @Override
                    public void onCliick(int poast) {
                        rechargeDlong(poast);
                    }
                });

                RecyclerView recyclerView1 = list.get(position).findViewById(R.id.rv_moneyreminder);
                recyclerView1.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView1.setAdapter(adapter);
                break;
            case 1:
                controlreAdapter = new ControlreAdapter(this);
                RecyclerView recyclerViewcont = list.get(position).findViewById(R.id.rv_controltwo);
                recyclerViewcont.setLayoutManager(new LinearLayoutManager(this));
                recyclerViewcont.setAdapter(controlreAdapter);
                break;
            case 2:
                List<Map> stringList = new ArrayList<>();
                SQLiteDatabase database = sqLdata.getWritableDatabase();
               Cursor cursor = database.query("CARRecharge",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        Map map1 = new HashMap();
                        map1.put("id",cursor.getString(cursor.getColumnIndex("id")));
                        map1.put("carid",cursor.getString(cursor.getColumnIndex("carid")));
                        map1.put("money",cursor.getString(cursor.getColumnIndex("money")));
                        map1.put("timedata",cursor.getString(cursor.getColumnIndex("timedata")));
                        stringList.add(map1);
                    }while (cursor.moveToNext());
                }
                Log.d(TAG, "setViewAdapter: 设置第三个页面的Adapter"+stringList.size());
                rechargeAdapteer = new RechargeAdapteer(this, stringList);
                RecyclerView recyclerViewrech = list.get(position).findViewById(R.id.rv_rech);
                recyclerViewrech.setLayoutManager(new LinearLayoutManager(this));
                recyclerViewrech.setAdapter(rechargeAdapteer);
                break;

        }

    }

    private void getDataJson() {
      thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (keyfist1) {
                        long startime = System.currentTimeMillis();
                        if (!RUN){
                            Thread.sleep(Long.MAX_VALUE);
                        }
                        listdatamoney.clear();
                        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                        MediaType mediaType = MediaType.parse("application/json");
                        for (int i = 1; i < 5; i++) {
                            Map map = new HashMap();
                            map.put("CarId", i);
                            map.put("UserName", "user1");
                            String parms = gson.toJson(map);
                            RequestBody requestBody = RequestBody.create(mediaType, parms);
                            Request request = new Request.Builder().post(requestBody).url(urljson).build();
                            okHttpClient = new OkHttpClient();
                            Response response = okHttpClient.newCall(request).execute();
                            String resuit = response.body().string();
                            JSONObject jsonObject = new JSONObject(resuit);
                            int money = jsonObject.getInt("Balance");
                            listdatamoney.add(money);
                            Log.d(TAG, "run: listdatamoney的相对大小"+listdatamoney.size());

                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (listdatamoney.size() !=4){
                                    listdatamoney.clear();
                                    Log.d(TAG, "run: listmoney的大小"+listdatamoney.size());
                                }else {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                        long endtime = System.currentTimeMillis();
                        if (endtime - startime < 5000) {
                            Thread.sleep(5000 - (endtime - startime));
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
        });
        thread.start();
    }

    private void rechargeDlong(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_rechargedlong, null);
        ImageView imageView = view.findViewById(R.id.im_deldlong);
        EditText editText = view.findViewById(R.id.et_moneydlong);
        Button confirm = view.findViewById(R.id.btn_moneyconfirm);
        Button cancel = view.findViewById(R.id.btn_cancel);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    editText.setText(s.subSequence(0, s.toString().length() - 1));
                    editText.setSelection(s.toString().length() - 1);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    if (Integer.parseInt(editText.getText().toString()) > 50) {
                        editText.setText("50");
                        Toastutil.showmes(MyCarActivity.this, "只能输入50的整数");
                    }
                }

            }
        });
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(view);
        dialog.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeCar(position, Integer.parseInt(editText.getText().toString()));
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateNowStr = sdf.format(date);
                SQLiteDatabase database = sqLdata.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("carid", position + "");
                values.put("money", editText.getText().toString());
                values.put("timedata", dateNowStr);
                database.insert("CARRecharge", null, values);
                dialog.dismiss();
            }
        });


    }

    private void RechargeCar(int carid, int Money) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");
                    Map map = new HashMap<>();
                    map.put("CarId", carid + 1);
                    map.put("Money", Money);
                    map.put("UserName", "user1");
                    String parms = gson.toJson(map);
                    Log.d("上传的json", parms);
                    RequestBody requestBody = RequestBody.create(mediaType, parms);
                    Request requestset = new Request.Builder().post(requestBody).url(urlset).build();
                    Response response = okHttpClient.newCall(requestset).execute();
                    String str = response.body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    String data = jsonObject.getString("RESULT");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (data.equals("S")) {
                                Toastutil.showmes(MyCarActivity.this, "充值成功");
                            } else {
                                Toastutil.showmes(MyCarActivity.this, "充值失败");
                            }
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

    @Override
    protected void onStop() {
        super.onStop();
        keyfist1 = false;
    }
}

