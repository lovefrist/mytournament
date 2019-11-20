package com.example.firsttopic.LightingManagement;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.RedGreenReed.GetAppdata;
import com.example.firsttopic.util.Toastutil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class LightManagActivity extends MyAppCompatActivity {
    private RecyclerView recyclerView;
    private OkHttpClient okHttpClient;
    private String urllemp = "http://192.168.3.5:8088/transportservice/action/GetTrafficLightConfigAction.do";
    private String urlset = "http://192.168.3.5:8088/transportservice/action/SetTrafficLightConfig.do";
    private List<GetAppdata> list = new ArrayList<>();
    private LempAdapter adapter;
    private Handler handler = new Handler();
    private List<Integer> checkBoxeslist = new ArrayList<>();
    private List<Button> buttonlist = new ArrayList<>();
    private Spinner spinner;
    private int sqining = 0;
    private Button button1, button2;
    private List<String> intlist;
    private List<CheckBox> boxnumlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Menu menu = super.setMenu(this, "红路灯管理", null);
        menu.getLinear_main_menu().setBackgroundColor(Color.rgb(62, 81, 181));
        setContentView(R.layout.activity_light_manag);
        recyclerView = findViewById(R.id.rv_lampsetup);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spinner = findViewById(R.id.sq_lamp);
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(this, R.layout.textfiast, getDataSource());
        spinneradapter.setDropDownViewResource(R.layout.breakcles);
//        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this,R.layout.spinnertext,getDataSource());
//        spinneradapter.setDropDownViewResource(R..layoutconnert);
        spinner.setAdapter(spinneradapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sqining = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button1 = findViewById(R.id.btn_lempquery);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setsort(sqining);


            }
        });
        button2 = findViewById(R.id.btn_batchsetup);
        button2.setOnClickListener(v -> {
            if (checkBoxeslist.size()==0){
                Toastutil.showmes(this,"请选中设置的选项");
            }else {

                setDlongdone(-1);
            }
        });
        adapter = new LempAdapter(LightManagActivity.this, list, new Getcbbtn() {


            @Override
            public void getChbex(int inp, boolean isCheck, CheckBox checkBox) {
                Log.d(TAG, "getChbex: 选中的按键位置" + inp);

                if (isCheck) {
                    boxnumlist.add(checkBox);
                    checkBoxeslist.add(inp);
                    buttonlist.get(inp - 1).setEnabled(true);
                    buttonlist.get(inp - 1).setOnClickListener(v -> setDlongdone(inp));
                } else {
                    buttonlist.get(inp - 1).setEnabled(false);
                    for (int i = 0; i < checkBoxeslist.size(); i++) {
                        if (checkBoxeslist.get(i) == inp) {
                            checkBoxeslist.remove(i);
                            boxnumlist.remove(i);
                        }
                    }
                }
                if (checkBoxeslist.size()>1){
                    for (int i=0;i<buttonlist.size();i++){
                        buttonlist.get(i).setEnabled(false);
                    }
                }else {
                    if (checkBoxeslist.size()!=0){
                        buttonlist.get(checkBoxeslist.get(0)-1).setEnabled(true);
                    }

                }
            }

            @Override
            public void getBtn(Button button) {
                buttonlist.add(button);

            }
        });

        getDataJson();

    }


    private void setDlongdone(int id) {
        Log.d(TAG, "setDlongdone:传入的id" + id);
        intlist = new ArrayList();
        List<EditText> editTextList = new ArrayList<>();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_lemp_topup, null);
        editTextList.add(view.findViewById(R.id.et_red));
        editTextList.add(view.findViewById(R.id.et_yoller));
        editTextList.add(view.findViewById(R.id.et_green));
        Button lmpconfirm = view.findViewById(R.id.btn_lmpconfirm);
        Button lempcancel = view.findViewById(R.id.btn_lempcancel);
        for (int i = 0; i < editTextList.size(); i++) {
            int finid = i;
            Log.d(TAG, "setDlongdone: "+i);
            editTextList.get(i).addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (start == 0 && s.toString().equals(".") && count == 1) {
                        //输入的第一个字符为"."
                        editTextList.get(finid).setText("");
                    } else if (s.length() >= 3 && count != 0) {
                        //当整数位数输入到达被要求的上限,并且当前在输入字符,而不是减少字符
                        editTextList.get(finid).setText(s.subSequence(0, s.toString().length() - 1));
                        editTextList.get(finid).setSelection(s.toString().length() - 1);
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                    String string = s.toString();
                    int len = string.length();
                    if (len >= 1 && string.startsWith("0")) { //如果输入框大于1开头数字为零的话就清楚输入框的内容
                        s.clear();
                    }
                }
            });
        }
        AlertDialog inputDialog = new AlertDialog.Builder(this).setView(view).create();
        inputDialog.show();
        lempcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
            }
        });//点击取消输入框消失
        lmpconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                for (int i=0;i<3;i++){
                    intlist.add(editTextList.get(i).getText().toString());

                    if (editTextList.get(i).getText().toString().equals("")){
                        check =false;
                    }
                }
                if (check){
                    setlemplong(id);
                    inputDialog.dismiss();
                }else {
                    intlist.clear();
                    Toast.makeText(LightManagActivity.this,"请输入设置的时间",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setlemplong(int id)  {
        new Thread(() -> {
            try {
                Log.d(TAG, "setlemplong: "+intlist.toString());
//        {"TrafficLightId":1,"RedTime":25,"GreenTime":35,"YellowTime":5,"UserName":"user1"}
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                MediaType mediaType = MediaType.parse("application/json");
                if (id != -1) {
                    Map map = new HashMap();
                    map.put("TrafficLightId", id);
                    map.put("RedTime", Integer.parseInt(intlist.get(0)));
                    map.put("GreenTime", Integer.parseInt(intlist.get(1)));
                    map.put("YellowTime", Integer.parseInt(intlist.get(2)));
                    map.put("UserName", "user1");

                    String params = gson.toJson(map);
                    RequestBody requestBody = RequestBody.create(mediaType, params);
                    Request request = new Request.Builder().post(requestBody).url(urlset).build();
                    okHttpClient = new OkHttpClient();
                    Response response = okHttpClient.newCall(request).execute();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toastutil.showmes(LightManagActivity.this, "充值成功");
                        }
                    });

                } else {
                    for (int i = 0; i < checkBoxeslist.size(); i++) {
                        Map map = new HashMap();
                        map.put("TrafficLightId", checkBoxeslist.get(i));
                        map.put("RedTime", Integer.parseInt(intlist.get(0)));
                        map.put("GreenTime", Integer.parseInt(intlist.get(1)));
                        map.put("YellowTime", Integer.parseInt(intlist.get(2)));
                        map.put("UserName", "user1");

                        String params = gson.toJson(map);
                        RequestBody requestBody = RequestBody.create(mediaType, params);
                        Request request = new Request.Builder().post(requestBody).url(urlset).build();
                        okHttpClient = new OkHttpClient();
                        Response response = okHttpClient.newCall(request).execute();
                        handler.post(() -> Toastutil.showmes(LightManagActivity.this, "设置成功"));
                    }

                }
                intlist.clear();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        getDataJson();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void getDataJson() {
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");
                    Map map = new HashMap<>();
                    map.put("TrafficLightId", i);
                    map.put("UserName", "user1");
                    String params = gson.toJson(map);
                    RequestBody requestBody = RequestBody.create(mediaType, params);
                    Request request = new Request.Builder().post(requestBody).url(urllemp).build();
                    okHttpClient = new OkHttpClient();
                    Response response = okHttpClient.newCall(request).execute();
                    final String resuit = response.body().string();
                    Log.d(TAG, "run: 得到Json" + resuit);
                    GetAppdata datalistjava = gson.fromJson(resuit, GetAppdata.class);

                    datalistjava.setId(i);
                    list.add(datalistjava);
                    int finid = i;
                    handler.post(() -> {

                        if (finid == 5) {
                            setsort(0);

                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setsort(int sorting) {
        switch (sorting) {
            case 0:
                //路口升序
                Collections.sort(list, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        if (o1.getId() > o2.getId()) return 1;
                        else return -1;
                    }
                });
                break;
            case 1:

//                Arrays.sort(appList, new Comparator<GetAppdata>() {
//                    @Override
//                    public int compare(GetAppdata o1, GetAppdata o2) {
//                        if(o1.getId() < o2.getId()) return 1;
//                        else return -1;
//                    }
//                });
                //路口降序
                Collections.sort(list, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        Log.d("改变的list", o1.getId() + "");
                        if (o1.getId() < o2.getId()) return 1;
                        else return -1;
                    }
                });
                break;
            case 2:
                //红灯升序
                Collections.sort(list, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        if (o1.getRedTime() > o2.getRedTime())
                            return 1;
                        else
                            return -1;
                    }
                });

                break;
            case 3:
                //红灯降序
                Collections.sort(list, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        if (o1.getRedTime() < o2.getRedTime())
                            return 1;
                        else
                            return -1;
                    }
                });
                break;

            case 4:
                //绿灯升序
                Collections.sort(list, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        if (o1.getGreenTime() > o2.getGreenTime())
                            return 1;
                        else return -1;
                    }
                });
                break;
            case 5:
                //绿灯降序
                Collections.sort(list, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        if (o1.getGreenTime() < o2.getGreenTime())
                            return 1;
                        else return -1;
                    }
                });
                break;
            case 6:
                //黄灯升序
                Collections.sort(list, (o1, o2) -> {
                    if (o1.getYellowTime() > o1.getYellowTime())
                        return 1;
                    else
                        return -1;
                });
                break;
            case 7:
                //黄灯降序
                Collections.sort(list, new Comparator<GetAppdata>() {
                    @Override
                    public int compare(GetAppdata o1, GetAppdata o2) {
                        if (o1.getYellowTime() < o1.getYellowTime())
                            return 1;
                        else
                            return -1;
                    }
                });
                break;


        }
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
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
}
