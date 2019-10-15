package com.example.firsttopic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.firsttop.firsttopActivity;
import com.example.firsttopic.therrtop.TherrtopActivity;
import com.example.firsttopic.twotop.GetAppdata;
import com.example.firsttopic.twotop.twotopAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class twotopActivity extends Activity {
    private ImageView imageView;
    private  int item_id = 1;
    private PopupWindow mpop;
    private Spinner mspsort;
    private Button mbuttonsotr;
    private String ulrjson = "http://192.168.3.5:8088/transportservice/action/GetTrafficLightConfigAction.do";
    private  OkHttpClient okHttpClient;
    private RecyclerView mrecyclerView;
    private  GetAppdata[] appList = new GetAppdata[5];
    Handler handler = new Handler();
    private ArrayList<GetAppdata> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twotop);
        imageView = findViewById(R.id.iv_imgget);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popText = getLayoutInflater().inflate(R.layout.layout_navyes, null);

                TextView myaccount = popText.findViewById(R.id.tv_myaccount);
                TextView redgrenn = popText.findViewById(R.id.tv_redgrenn);
                TextView zhangdan = popText.findViewById(R.id.tv_zhangdan);
                OnClick onClick =  new OnClick();
                zhangdan.setOnClickListener(onClick);
                myaccount.setOnClickListener(onClick);
                redgrenn.setOnClickListener(onClick);

                mpop = new PopupWindow(popText, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
                mpop.setOutsideTouchable(true);
                mpop.setFocusable(true);
                mpop.showAsDropDown(imageView);
            }
        });

        mspsort = findViewById(R.id.sp_twosort);
        getDatajson();
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(twotopActivity.this, R.layout.textfiast, getDataSource());
        spinneradapter.setDropDownViewResource(R.layout.breakcles);
//        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this,R.layout.spinnertext,getDataSource());
//        spinneradapter.setDropDownViewResource(R..layoutconnert);
        mspsort.setAdapter(spinneradapter);
        mspsort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("在第几个排序方式", "数值: "+position);
                item_id = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mbuttonsotr = findViewById(R.id.btn_twoquery);
        mbuttonsotr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String datarote  =  mspsort.getSelectedItem().toString();
              Log.d("排序方式是",datarote);
                setsort(item_id);
            }
        });


        mrecyclerView = findViewById(R.id.rv_getdata);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(twotopActivity.this));


    }
    public List<String> getDataSource() {
        List<String> sqinnerList = new ArrayList<String>();
        sqinnerList.add("路口升序");
        sqinnerList.add("路口降序");
        sqinnerList.add("红灯升序");
        sqinnerList.add("红灯降序");
        sqinnerList.add("绿灯升序");
        sqinnerList.add("绿灯降序");
        sqinnerList.add("黄灯升序");
        sqinnerList.add("黄灯降序");
        return sqinnerList;
//        路口升序、路口降序、红灯升序、红灯降序、绿灯升序、绿灯降序、黄灯升序和黄灯降序
    }

        public void getDatajson(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 1;i<=5;i++){
                        Log.d("开始获取",""+i);
                        Gson gson = new Gson();
                        MediaType mediaType = MediaType.parse("application/json");
                        Map map = new HashMap<>();
                        map.put("TrafficLightId", i);
                        map.put("UserName", "user1");
                        String parms = gson.toJson(map);
                        RequestBody requestBody = RequestBody.create(mediaType, parms);
                        Request request = new Request.Builder().post(requestBody).url(ulrjson).build();
                        okHttpClient = new OkHttpClient();
                        Response response = okHttpClient.newCall(request).execute();

                        final String resuit = response.body().string();
                        response.body().close();
                        Log.d("获取的json的是", resuit + "");
                        Log.d("进来了解析json", "开始解析");
                        appList[i-1] = gson.fromJson(resuit, GetAppdata.class);
                        appList[i-1].setId(i);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                        mrecyclerView.setAdapter(new twotopAdapter(twotopActivity.this,appList));
                        }
                    });

                } catch (IOException e) {
                    Log.d("IOException", "IOException");
                    e.printStackTrace();
                }
            }
        }).start();

        }


    class OnClick implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.tv_myaccount:
                    Log.d("开始页面的跳转","开始了");
                    mpop.dismiss();
                    intent = new Intent(twotopActivity.this, firsttopActivity.class);
                    break;
                case R.id.tv_redgrenn:
                    mpop.dismiss();
                    intent = new Intent(twotopActivity.this, twotopActivity.class);
                    break;
                case R.id.tv_zhangdan:
                    mpop.dismiss();
                    intent = new Intent(twotopActivity.this, TherrtopActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
    private void setsort(int sorting){
        switch (sorting){
            case 0:
//                Collections.sort(list);
                Collections.sort(list, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        if(o1.getId() > o2.getId()) return 1;
                        else return -1;
                    }
                });
                break;
            case 1:
                Arrays.sort(appList, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        if(o1.getRedTime() > o2.getRedTime()) return 1;
                        else return -1;
                    }
                });
                break;
            case 2:
                Arrays.sort(appList, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        if(o1.getId() < o2.getId()) return 1;
                        else return -1;
                    }
                });
                break;
            case 3:

                break;
            case 4:
                break;
            case 5:

            case 6:
                break;
            case 7:
                break;


        }
        mrecyclerView.setAdapter(new twotopAdapter(twotopActivity.this,appList));
    }
}
