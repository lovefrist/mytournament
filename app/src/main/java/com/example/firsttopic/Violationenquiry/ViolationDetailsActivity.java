package com.example.firsttopic.Violationenquiry;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.MultiMedia.ViolationrecordActivity;
import com.example.firsttopic.GetSetfile.MyDataViolatirHelper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViolationDetailsActivity extends MyAppCompatActivity {
    private RecyclerView recyclerViewring, recyclerViewlift;
    private ImageView imageView;
    private String urlone = "http://192.168.3.5:8088/transportservice/action/GetCarPeccancy.do";
    private String urlCode = "http://192.168.3.5:8088/transportservice/action/GetPeccancyType.do";
    private String TAG = "ViolationDetailsActivity";
    private String carid;
    private OkHttpClient okHttpClient;
    private List<Map> list1 = new ArrayList<>();
    private List<Map> list2 = new ArrayList<>();
    private List<Map<String, String>> listnum = new ArrayList<>();
    private List<Map<String, String>> listnumleft = new ArrayList<>();
    private Handler handler = new Handler();
    private ViodetailsAdapter adapter;
    private ViolationcardAdapter violationcardAdapter;
    private MyDataViolatirHelper dbhoder;
    private List<View> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Menu menu = super.setMenu(this, "违章查询", null);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
        setContentView(R.layout.activity_violation_details);
        Intent intent = getIntent();
        carid = intent.getStringExtra("carid");
        dbhoder = new MyDataViolatirHelper(this, "Violationhistory", null, 2);
        recyclerViewlift = findViewById(R.id.rv_datalis);
        recyclerViewlift.setLayoutManager(new LinearLayoutManager(this));
        violationcardAdapter = new ViolationcardAdapter(this, listnumleft, new Vioinf() {

            @Override
            public void getImageonClick(String string,int porast) {
                SQLiteDatabase database = dbhoder.getWritableDatabase();
                Log.d(TAG, "getImageonClick: 删除的车牌号"+string);
                database.delete("Violation", "carnumber = ?", new String[]{string});
                listnumleft.clear();
              Cursor cursor =  database.query("Violation",null,null,null,null,null,null);
                if (cursor.moveToFirst()) {
                    do {
                        Map<String, String> map1 = new HashMap();
                        String carnumber = cursor.getString(cursor.getColumnIndex("carnumber"));
                        String pmoney = cursor.getString(cursor.getColumnIndex("pmoney"));
                        String pscore = cursor.getString(cursor.getColumnIndex("pscore"));
                        String number = cursor.getString(cursor.getColumnIndex("number"));
                        map1.put("carnumber", carnumber);
                        map1.put("pmoney", pmoney);
                        map1.put("pscore", pscore);
                        map1.put("number", number);
                        listnumleft.add(map1);

                    } while (cursor.moveToNext());

                }else {
                    listnum.clear();
                    adapter.notifyDataSetChanged();
                }
                if (listnumleft.size() != 0) {
                    setringleft(listnumleft.get(0).get("carnumber"));
                }

                for (int i = 0;i<list.size();i++){
                    if (i == porast){
                        list.remove(i);
                    }
                }
                list.clear();
                recyclerViewlift.setAdapter(violationcardAdapter);
            }

            @Override
            public void getLayoutonClick(String carnum,int poast) {
                listnum.clear();
                list1.clear();
                list2.clear();
                setringleft(carnum);
                for (int i = 0;i<list.size();i++){
                    if (i == poast){
                        list.get(i).setBackgroundResource(R.drawable.violat_chane_adapter);
                    }else {
                        list.get(i).setBackgroundResource(R.drawable.violat_recycler_adapter);
                    }
                }

            }

            @Override
            public void getLayoutonView(View view) {
                list.add(view);
            }
        });
        recyclerViewlift.setAdapter(violationcardAdapter);
        recyclerViewring = findViewById(R.id.rv_details);
        recyclerViewring.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ViodetailsAdapter(this, listnum, new VioMyview() {
            @Override
            public void onclick() {
                Intent intent1 = new Intent(ViolationDetailsActivity.this, ViolationrecordActivity.class);
                startActivity(intent1);
            }
        });
        recyclerViewring.setAdapter(adapter);
        imageView = findViewById(R.id.iv_plus);
        imageView.setOnClickListener(v -> {
            addDlong();
        });
        getdataJson();
    }

    private void getdataJson() {
        new Thread(() -> {
            try {
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                MediaType mediaType = MediaType.parse("application/json");
                String url;
                for (int i = 0; i < 2; i++) {
                    if (i != 0) {
                        url = urlone;
                    } else {
                        url = urlCode;
                    }
                    Map map = new HashMap<>();
                    if (url != urlone) {
                        map.put("UserName", "user1");
                    } else {
                        map.put("UserName", "user1");
                        map.put("carnumber", carid);
                    }

                    String params = gson.toJson(map);
                    Log.d(TAG, "run: url" + url);
                    Log.d(TAG, "run:JSOn " + params);
                    RequestBody requestBody = RequestBody.create(mediaType, params);
                    Request request = new Request.Builder().post(requestBody).url(url).build();
                    okHttpClient = new OkHttpClient();
                    Response response = null;
                    response = okHttpClient.newCall(request).execute();
                    final String resuit = response.body().string();

                    JSONObject jsonObject = new JSONObject(resuit);
                    JSONArray jsonArray = jsonObject.getJSONArray("ROWS_DETAIL");
                    if (url != urlone) {
                        for (int j = 0; j < jsonArray.length(); j++) {
                            Map map1 = new HashMap();
                            JSONObject arrayObject1 = jsonArray.getJSONObject(j);
                            String pcode = arrayObject1.getString("pcode");
                            String premarks = arrayObject1.getString("premarks");
                            String pmoney = arrayObject1.getString("pmoney");
                            String pscore = arrayObject1.getString("pscore");
                            map1.put("pcode", pcode);
                            map1.put("premarks", premarks);
                            map1.put("pmoney", pmoney);
                            map1.put("pscore", pscore);
                            list1.add(map1);
                        }
                    } else {
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject arrayObject1 = jsonArray.getJSONObject(j);
                            Map map1 = new HashMap();
                            String pcode = arrayObject1.getString("pcode");
                            String pdatetime = arrayObject1.getString("pdatetime");
                            String paddr = arrayObject1.getString("paddr");
                            map1.put("pcode", pcode);
                            map1.put("pdatetime", pdatetime);
                            map1.put("paddr", paddr);
                            list2.add(map1);
                        }
                    }
                    if (i == 1) {
                        for (int j = 0; j < list2.size(); j++) {

                            for (int k = 0; k < list1.size(); k++) {
                                if (list1.get(k).get("pcode").equals(list2.get(j).get("pcode"))) {

                                    Map map1 = new HashMap();
                                    map1.put("pmoney", list1.get(k).get("pmoney"));
                                    map1.put("pscore", list1.get(k).get("pscore"));
                                    map1.put("paddr", list2.get(j).get("paddr"));
                                    map1.put("pdatetime", list2.get(j).get("pdatetime"));
                                    map1.put("premarks", list1.get(k).get("premarks"));
                                    listnum.add(map1);
                                }
                            }
                        }
                        Map map1 = new HashMap();
                        map1.put("carnumber", carid);
                        map1.put("number", listnum.size() + "");
                        int numpscore = 0;
                        int numpmoney = 0;
                        for (int j = 0; j < listnum.size(); j++) {
                            numpmoney += Integer.parseInt(listnum.get(j).get("pmoney"));
                            numpscore += Integer.parseInt(listnum.get(j).get("pscore"));
                        }
                        map1.put("pscore", numpscore + "");
                        map1.put("pmoney", numpmoney + "");
                        List<Map<String, String>> mapList = new ArrayList<>();
                        mapList.add(map1);
                        listnumleft.add(map1);
                        qurySQL(mapList);
                        addSQLdata(mapList);
                        handler.post(() -> {
                            adapter.notifyDataSetChanged();
                            list.clear();
                            recyclerViewlift.setAdapter(violationcardAdapter);
                        });

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();

    }

   private void setringleft(String namecar){
       new Thread(() -> {
           try {
               Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
               MediaType mediaType = MediaType.parse("application/json");
               String url;
               for (int i = 0; i < 2; i++) {
                   if (i != 0) {
                       url = urlone;
                   } else {
                       url = urlCode;
                   }
                   Map map = new HashMap<>();
                   if (url != urlone) {
                       map.put("UserName", "user1");
                   } else {
                       map.put("UserName", "user1");
                       map.put("carnumber", namecar);
                   }

                   String params = gson.toJson(map);
                   Log.d(TAG, "run: url" + url);
                   Log.d(TAG, "run:JSOn " + params);
                   RequestBody requestBody = RequestBody.create(mediaType, params);
                   Request request = new Request.Builder().post(requestBody).url(url).build();
                   okHttpClient = new OkHttpClient();
                   Response response = null;
                   response = okHttpClient.newCall(request).execute();
                   final String resuit = response.body().string();

                   JSONObject jsonObject = new JSONObject(resuit);
                   JSONArray jsonArray = jsonObject.getJSONArray("ROWS_DETAIL");
                   if (url != urlone) {
                       for (int j = 0; j < jsonArray.length(); j++) {
                           Map map1 = new HashMap();
                           JSONObject arrayObject1 = jsonArray.getJSONObject(j);
                           String pcode = arrayObject1.getString("pcode");
                           String premarks = arrayObject1.getString("premarks");
                           String pmoney = arrayObject1.getString("pmoney");
                           String pscore = arrayObject1.getString("pscore");
                           map1.put("pcode", pcode);
                           map1.put("premarks", premarks);
                           map1.put("pmoney", pmoney);
                           map1.put("pscore", pscore);
                           list1.add(map1);
                       }
                   } else {
                       for (int j = 0; j < jsonArray.length(); j++) {
                           JSONObject arrayObject1 = jsonArray.getJSONObject(j);
                           Map map1 = new HashMap();
                           String pcode = arrayObject1.getString("pcode");
                           String pdatetime = arrayObject1.getString("pdatetime");
                           String paddr = arrayObject1.getString("paddr");
                           map1.put("pcode", pcode);
                           map1.put("pdatetime", pdatetime);
                           map1.put("paddr", paddr);
                           list2.add(map1);
                       }
                   }
                   if (i == 1) {
                       for (int j = 0; j < list2.size(); j++) {

                           for (int k = 0; k < list1.size(); k++) {
                               if (list1.get(k).get("pcode").equals(list2.get(j).get("pcode"))) {

                                   Map map1 = new HashMap();
                                   map1.put("pmoney", list1.get(k).get("pmoney"));
                                   map1.put("pscore", list1.get(k).get("pscore"));
                                   map1.put("paddr", list2.get(j).get("paddr"));
                                   map1.put("pdatetime", list2.get(j).get("pdatetime"));
                                   map1.put("premarks", list1.get(k).get("premarks"));
                                   listnum.add(map1);
                               }
                           }
                       }

                       handler.post(() -> {
                           adapter.notifyDataSetChanged();

                       });

                   }

               }

           } catch (IOException e) {
               e.printStackTrace();
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }).start();

   }
    private void addSQLdata(List<Map<String, String>> list) {

        boolean bol = true;
        SQLiteDatabase database = dbhoder.getWritableDatabase();
        Cursor cursor = database.query("Violation", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                bol = !cursor.getString(cursor.getColumnIndex("carnumber")).equals(list.get(0).get("carnumber"));
                if (!bol) {
                    break;
                }
            } while (cursor.moveToNext());
        }
        if (bol) {
            ContentValues values = new ContentValues();
            values.put("carnumber", list.get(0).get("carnumber"));
            values.put("pmoney", list.get(0).get("pmoney"));
            values.put("pscore", list.get(0).get("pscore"));
            values.put("number", list.get(0).get("number"));
            database.insert("Violation", null, values);
            Log.d(TAG, "addSQLdata: 添加成功");
        }

    }

    private void qurySQL(List<Map<String, String>> mapList) {

        SQLiteDatabase database = dbhoder.getWritableDatabase();
        Cursor cursor = database.query("Violation", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Map map1 = new HashMap();

                String carnumber = cursor.getString(cursor.getColumnIndex("carnumber"));
                Log.d(TAG, "qurySQL:车牌号" + carnumber);
                String pmoney = cursor.getString(cursor.getColumnIndex("pmoney"));
                String pscore = cursor.getString(cursor.getColumnIndex("pscore"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                map1.put("carnumber", carnumber);
                map1.put("pmoney", pmoney);
                map1.put("pscore", pscore);
                map1.put("number", number);
                    if (!mapList.get(0).get("carnumber").equals(carnumber)) {
                        listnumleft.add(map1);
                    }

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void addDlong() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_input_violation, null);
        EditText editText = view.findViewById(R.id.et_addviolatiom);
        Button button = view.findViewById(R.id.btn_addInquire);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && s.toString().equals(".") && count == 1) {
                    //输入的第一个字符为"."
                    editText.setText("");
                } else if (s.length() > 6 && count != 0) {
                    //当整数位数输入到达被要求的上限,并且当前在输入字符,而不是减少字符
                    editText.setText(s.subSequence(0, s.toString().length() - 1));
                    editText.setSelection(s.toString().length() - 1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                int len = s1.length();
                if (len >= 1 && s1.startsWith("0")) ;
            }
        });

        AlertDialog inputDialog = new AlertDialog.Builder(this).setView(view).create();
        inputDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listnum.clear();
                listnumleft.clear();
                list1.clear();
                list2.clear();
                carid = "鲁" + editText.getText().toString();
                getdataJson();
                inputDialog.dismiss();
            }
        });
    }


}
