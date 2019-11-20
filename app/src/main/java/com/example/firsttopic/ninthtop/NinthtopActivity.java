package com.example.firsttopic.ninthtop;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.GetSetfile.MyDatabaseHelper;
import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.BillManag.TherrtopActivity;
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
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NinthtopActivity extends MyAppCompatActivity implements View.OnClickListener {
    private View view;
    private TextView alltextView, mrecordtext;
    private List<HashMap<String, String>> list;
    private List<HashMap<String, Integer>> listioc;
    private RecyclerView mrecyclerView;
    public List<Integer> datavalue = new ArrayList<>();
    private int Serial;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String urljson = "http://192.168.3.5:8088/transportservice/action/GetCarAccountBalance.do";
    private String urlset = "http://192.168.3.5:8088/transportservice/action/SetCarAccountRecharge.do";
    private MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "CARRecharge", null, 7);
    private HashMap<Integer, View> btnmap = new HashMap<>();
    private HashMap<Integer, CheckBox> chexmap = new HashMap<>();
    private Handler handler = new Handler();
    private List<String> manlist = new ArrayList<>();
    private List<String> copymanlist;
    private List<TextView> mantextlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        /************************************************************顶部右边的布局设置****************************************************/
        Menu menu = super.setMenu(this, "账号管理", R.layout.layput_ringht_title);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
        try {
            view = menu.getLinear_main_more();

        } catch (Exception e) {
            e.printStackTrace();
        }
        getjson();

        alltextView = view.findViewById(R.id.tv_Rechargeall);//找到布局的控件
        mrecordtext = view.findViewById(R.id.tv_recordall);//找到布局的控件
        mrecordtext.setOnClickListener(this);//设置点击事情
        alltextView.setOnClickListener(this);//设置批量处理的事情
        /*--------------------------------------设置reyclerview的adapter--------------------------------*/
        mrecyclerView = findViewById(R.id.rv_account);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*---------------------------------------------调用接口经行Java文件之间的传值---------------------------------------------------------------------*/

    }

    /*-------------------------------------------给标题栏右边的设置点击效果--------------------------------------------------*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recordall:
                Intent intent = new Intent(NinthtopActivity.this, TherrtopActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_Rechargeall:
                if (datavalue.size() != 0) {

                    showInputDialog(-1);

                } else {
                    Toast.makeText(NinthtopActivity.this, "请勾选充值的勾勾", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void getdatahm() {
        Random random = new Random();
        list = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        nameList.add("张三");
        nameList.add("李四");
        nameList.add("王五");
        nameList.add("赵六");
        List<Integer> carlogList = new ArrayList<>();
        carlogList.add(R.drawable.bmnumber);
        carlogList.add(R.drawable.fllico);
        carlogList.add(R.drawable.bcico);
        carlogList.add(R.drawable.chevroletlogo);
        for (int i = 0; i < 4; i++) {
            list.add(new HashMap<String, String>());
        }

        for (int i = 0; i < list.size(); i++) {
            int finid = i + 1;
            list.get(i).put("id", "" + finid);
            list.get(i).put("carid", "" + carlogList.get(i));
            list.get(i).put("carnum", "辽A1000" + finid);
            list.get(i).put("name", nameList.get(i));
            list.get(i).put("balance", "" +manlist.get(i));
        }

    }

    /*-------------------------------------------------------设置弹窗----------------------------------------*/
    private void showInputDialog(int idc) { //设置弹出框

        View view = LayoutInflater.from(this).inflate(R.layout.layout_input, null);
        final EditText editText = view.findViewById(R.id.et_maneynum);
        final TextView textView = view.findViewById(R.id.tv_carid);
        final Button btnconfirm = view.findViewById(R.id.btn_confirm);
        final Button btncancel = view.findViewById(R.id.btn_cancel);
        String carid = "";
        if (idc == -1) {
            for (int i = 0; i < datavalue.size(); i++) {
                int finid = i + 1;
                carid = carid + list.get(i).put("carnum", "辽A1000" + finid);
            }
        } else {
            carid = list.get(idc - 1).put("carnum", "辽A1000" + idc);
        }
        textView.setText(carid);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && s.toString().equals(".") && count == 1) {
                    //输入的第一个字符为"."
                    editText.setText("");
                } else if (s.length() >= 4 && count != 0) {
                    //当整数位数输入到达被要求的上限,并且当前在输入字符,而不是减少字符
                    editText.setText(s.subSequence(0, s.toString().length() - 1));
                    editText.setSelection(s.toString().length() - 1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                int len = string.length();
                if (len >= 1 && string.startsWith("0")) {
                    s.clear();
                }
            }
        });

        AlertDialog inputDialog = new AlertDialog.Builder(NinthtopActivity.this).setTitle("车辆充值").setView(view).create();


        btncancel.setOnClickListener(v -> {

            if (!editText.getText().toString().equals("")) {
                RechargeBalance(idc, Integer.parseInt(editText.getText().toString()));
                inputDialog.dismiss();
                setallche();
                setbtnchoice();

            } else
                Toast.makeText(NinthtopActivity.this, "充值金额不能为零", Toast.LENGTH_SHORT).show();

        });

        btnconfirm.setOnClickListener(v -> {
            Toast.makeText(NinthtopActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
            inputDialog.dismiss();
        });
        inputDialog.show();
    }


    private void adddatas(int idc, int number) { //添加充值记录
        SQLiteDatabase adddata = dbHelper.getWritableDatabase();
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateNowStr = sdf.format(date);
        ContentValues values = new ContentValues();
        values.put("id", idc);
        String strcarid = list.get(idc - 1).put("carnum", "辽A1000" + idc);
        values.put("carid", strcarid);
        values.put("manoy", number);
        values.put("username", "admin");
        values.put("time", dateNowStr);
        long num = adddata.insert("CAR", null, values);
        Log.d("添加成功", "成功了" + num);
        values.clear();
    }

    private void RechargeBalance(int idc, int number) { // 传入汽车的id进行充值
        Log.d("进入到充值入口", "正在充值中");

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");
                    if (idc == -1) {
                        for (int i = 0; i < datavalue.size(); i++) {
                            Map map = new HashMap<>();
                            map.put("CarId", i + 1);
                            map.put("Money", number);
                            map.put("UserName", "user1");
                            String parms = gson.toJson(map);
                            Log.d("上传的json", parms);
                            RequestBody requestBody = RequestBody.create(mediaType, parms);
                            Request requestset = new Request.Builder().post(requestBody).url(urlset).build();
                            okHttpClient.newCall(requestset).execute();
                            int finid = i+1;
                            handler.post(() -> {
                                getjson();
                                adddatas(finid,number);
                            });
                        }
                    } else {
                        Map map = new HashMap<>();
                        map.put("CarId", idc);
                        map.put("Money", number);
                        map.put("UserName", "user1");
                        String parms = gson.toJson(map);
                        Log.d("上传的json", parms);
                        RequestBody requestBody = RequestBody.create(mediaType, parms);
                        Request requestset = new Request.Builder().post(requestBody).url(urlset).build();
                        okHttpClient.newCall(requestset).execute();
                        handler.post(() -> {
                            getjson();
                            adddatas(idc,number);
                            Toast.makeText(NinthtopActivity.this,"充值成功",Toast.LENGTH_SHORT).show();
                        });
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("充值成功", "充值成功了");
            }
        }).start();

    }

    private void setchoice(int carid, boolean state) {
        if (state) {
            btnmap.get(carid - 1).setEnabled(true);
        } else {
            btnmap.get(carid - 1).setEnabled(false);

        }

    }

    private void setbtnchoice() {
        Log.d(TAG, "setbtnchoice: 进入设置按键不可点击方法");
        for (int i = 0; i < btnmap.size(); i++) {
            btnmap.get(i).setEnabled(false);
        }
    }

    private void setallche() { // 取消单选框的选中
        Log.d(TAG, "setallche: 进入设置按键的方法" + chexmap.size());
        for (int i = 0; i < chexmap.size(); i++) {
            Log.d(TAG, "setallche: 开始取消选择");
            chexmap.get(i).setChecked(false);

        }
    }

    public void getjson() {
        new Thread(() -> {
            try {
                String url;
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                Gson gson1 = new Gson();
                MediaType mediaType = MediaType.parse("application/json");
                Map map = new HashMap<>();
                for (int i = 0; i < 4; i++) {
                    url = urljson;
                    map.put("CarId", i + 1);
                    map.put("UserName", "user1");
                    String parms = gson.toJson(map);
                    RequestBody requestBody = RequestBody.create(mediaType, parms);
                    Request request = new Request.Builder().post(requestBody).url(url).build();
                    okHttpClient = new OkHttpClient();
                    Response response = okHttpClient.newCall(request).execute();
                    String resuit = response.body().string();
                    int fint = i;
                    handler.post(() -> {
                        parseJSON(resuit);
                        if (fint == 3){
                            getdatahm();
                            CarmationAdapter carmationAdapter = new CarmationAdapter(this, list, new getDataValue() {//通过接口来进行传值
                                @Override
                                public void getValue(int i, boolean t) { //获取chebox是否选中
                                    setchoice(i, t);
                                    if (t) {
                                        datavalue.add(i);
                                        Log.d(TAG, "onCreate: " + datavalue.toString());
                                    } else {
                                        for (int j = 0; j < datavalue.size(); j++) {
                                            int vlaue = datavalue.get(j);
                                            if (vlaue == i) {
                                                datavalue.remove(j);
                                                Log.d(TAG, "onCreate: " + datavalue.toString());
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void getView(int it) {//获取按键的值

                                    view.setEnabled(false);
                                    showInputDialog(it);
                                }

                                @Override
                                public void getbtnview(int id, View view) {
                                    btnmap.put(id, view);
                                    Log.d(TAG, "getbtnview: 按键的数" + btnmap.size());
                                }

                                @Override
                                public void getchexview(int id, CheckBox checkBox) {
                                    chexmap.put(id, checkBox);
                                    Log.d(TAG, "getchexview: 按键的数量" + chexmap.size());
                                }

                                @Override
                                public void getTextView(TextView textView) {
                                    mantextlist.add(textView);
                                }
                            });
                            mrecyclerView.setAdapter(carmationAdapter);
                            copymanlist = manlist;
                            Changemaneyback();
                            manlist.clear();

                        }

                    });
                    response.body().close();
                    map.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();


    }

    private void parseJSON(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            String manoy = jsonObject.getString("Balance");
            manlist.add(manoy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Changemaneyback(){
        for (int i =0;i<manlist.size();i++){
           if(Integer.parseInt(manlist.get(i)) <50) {
               mantextlist.get(i).setBackgroundColor(Color.rgb(255,0,0));

           }
        }

    }

}
