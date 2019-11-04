package com.example.firsttopic.transportquery;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

public class TransportQueryActivity extends MyAppCompatActivity {
    private ExpandableListView expandableListView;
    private String urlcapacity = "http://192.168.3.5:8088/transportservice/action/GetBusCapacity.do";
    private String urldistance = "http://192.168.3.5:8088/transportservice/action/GetBusStationInfo.do";
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler();
    private List<HashMap<String, String>> alllist = new ArrayList<>();
    private XendlistAdapter adapter;
    private List<Map>[] distancelist = new ArrayList[2];
    private final String TAG = "TransportQueryActivity";
    private final double ms = 5.56;
    private boolean setbool = true;
    private TextView textView;
    private int datanum = 0;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Menu menu = super.setMenu(this, "出行管理", null);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
        setContentView(R.layout.activity_transport_query);
        expandableListView = findViewById(R.id.elv_details);
        textView = findViewById(R.id.tv_allpople);
        for (int i = 0; i < distancelist.length; i++) {
            distancelist[i] = new ArrayList<>();
        }
        adapter = new XendlistAdapter(this, distancelist);
        expandableListView.setAdapter(adapter);
        button = findViewById(R.id.btn_setbtntan);
        button.setOnClickListener(v -> {
            showrecycleDialog();
        });
        getjson();

    }

    public void getjson() {
        new Thread(() -> {
            while (setbool) {
                long startTime = System.currentTimeMillis();
                try {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");
                    for (int i = 0; i < 2; i++) {
                        Map map = new HashMap<>();
                        map.put("BusStationId", (i + 1));
                        map.put("UserName", "user1");
                        String params = gson.toJson(map);
                        RequestBody requestBody = RequestBody.create(mediaType, params);
                        Request request = new Request.Builder().post(requestBody).url(urldistance).build();
                        okHttpClient = new OkHttpClient();
                        Response response = okHttpClient.newCall(request).execute();
                        final String resuit = response.body().string();
                        JSONObject jsonObject = new JSONObject(resuit);
                        JSONArray arrayLis = jsonObject.getJSONArray("ROWS_DETAIL");
                        List<Map> li = new ArrayList();
                        for (int j = 0; j < distancelist.length; j++) {
                            int carid = arrayLis.getJSONObject(j).getInt("BusId");
                            int distance = arrayLis.getJSONObject(j).getInt("Distance") / 10;
                            Map map2 = new HashMap<>();
                            map2.put("BusId", (j + 1));
                            map2.put("UserName", "user1");
                            String parms = gson.toJson(map2);
                            RequestBody requestBody1 = RequestBody.create(mediaType, parms);
                            Request request1 = new Request.Builder().post(requestBody1).url(urlcapacity).build();
                            okHttpClient = new OkHttpClient();
                            Response response1 = okHttpClient.newCall(request1).execute();
                            final String resuit1 = response1.body().string();
                            JSONObject jsonObject1 = new JSONObject(resuit1);
                            Map map1 = new HashMap();
                            map1.put("车牌号", carid);
                            map1.put("距离", distance);
                            map1.put("时间", distance / 334);
                            map1.put("容量", jsonObject1.getInt("BusCapacity"));
                            datanum += jsonObject1.getInt("BusCapacity");
                            li.add(map1);
                        }
                        distancelist[i].clear();
                        distancelist[i] = li;
                    }
                    handler.post(() -> {
                        for (int i = 0; i < distancelist.length; i++) {
                            Collections.sort(distancelist[i], new Comparator<Map>() {
                                @Override
                                public int compare(Map o1, Map o2) {
                                    if (Integer.parseInt(o1.get("距离").toString()) > Integer.parseInt(o2.get("距离").toString()))
                                        return 0;
                                    else
                                        return -1;
                                }
                            });
                        }
                        textView.setText(datanum + "人");
                        adapter.notifyDataSetChanged();

                        datanum = 0;

                    });
                    long endTime = System.currentTimeMillis();
                    try {
                        Thread.sleep(3000 - (endTime - startTime));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        setbool = false;
    }

    private void showrecycleDialog() {
        Log.d(TAG, "showrecycleDialog: 弹出框 ");
        View view = LayoutInflater.from(this).inflate(R.layout.layout_transpot_adapter, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_passengernum);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerDlongAdapter recyclerDlongAdapter = new RecyclerDlongAdapter(this,addlist());
        recyclerView.setAdapter(recyclerDlongAdapter);
        Button button = view.findViewById(R.id.btn_disappear);

        AlertDialog inputDialog = new AlertDialog.Builder(this).setView(view).create();
        inputDialog.show();
        button.setOnClickListener(v -> {
            inputDialog.dismiss();
        });
    }

    private List<Map<String, Integer>> addlist() {
        List<Map<String, Integer>> lists = new ArrayList<>();
        for (int i=0;i<15;i++){
            Map<String, Integer> map = new HashMap<>();
            map.put("serial",1);
            map.put("busnum",1);
            map.put("pople",100);
            lists.add(map);
        }

      return lists;

    }
}
