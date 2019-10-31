package com.example.firsttopic.firsttop;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firsttopic.GetSetfile.MyDatabaseHelper;
import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.therrtop.TherrtopActivity;
import com.example.firsttopic.twotop.twotopActivity;
import com.example.firsttopic.util.Toastutil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class firsttopActivity extends MyAppCompatActivity {
    private ImageView imageView;
    private PopupWindow mpop;
    private Spinner spinner;
    private Button buttonquery, btnquery;
    private OkHttpClient okHttpClient;
    private String urljson = "http://192.168.3.5:8088/transportservice/action/GetCarAccountBalance.do";
    private String urlset = "http://192.168.3.5:8088/transportservice/action/SetCarAccountRecharge.do";
    private TextView tv_sql;
    private int keyid = 1;
    private Handler handler = new Handler();
    private EditText medittext;
    private MyDatabaseHelper dbHelper;
    private int numberrech;
    private int serial;
    /* 整数位数*/
    private static final int INTEGER_COUNT = 3;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firsttop);
        Menu menu = super.setMenu(this, "ETC", null);
        View view = menu.getLinear_left();
        view.setBackgroundColor(Color.rgb(255, 255, 255));
        dbHelper = new MyDatabaseHelper(this, "CARRecharge", null, 7);
        getdataserial();
        tv_sql = findViewById(R.id.tv_yuen);

        spinner = findViewById(R.id.sq_number);
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(firsttopActivity.this, R.layout.textfiast, getDataSource());
        spinneradapter.setDropDownViewResource(R.layout.breakcles);
        spinner.setAdapter(spinneradapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languages = getResources().getStringArray(R.array.chehao);
                keyid = position + 1;
                if (position == 0) {
                    getjson();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonquery = findViewById(R.id.btn_query);
        buttonquery.setOnClickListener(v -> getjson());
        btnquery = findViewById(R.id.btn_Log);
        medittext = findViewById(R.id.et_Recharge);
        medittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && s.toString().equals(".") && count == 1) {
                    //输入的第一个字符为"."
                    medittext.setText("");
                } else if (s.length() >= INTEGER_COUNT + 1 && count != 0) {
                    //当整数位数输入到达被要求的上限,并且当前在输入字符,而不是减少字符
                    medittext.setText(s.subSequence(0, s.toString().length() - 1));
                    medittext.setSelection(s.toString().length() - 1);
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
        btnquery.setOnClickListener(v -> {
            if (!medittext.getText().toString().equals("")) {
                serial++;
                Log.d("充值的金额为", medittext.getText().toString());
                numberrech = Integer.parseInt(medittext.getText().toString());
                Log.d("表单传入的", "" + medittext.getText().toString());

                if (numberrech < 1000 && numberrech > 0) {
                    RechargeBalance();
                    getjson();
                    adddatas(numberrech);
                    Toastutil.showmes(firsttopActivity.this, "充值成功");
                } else {
                    Toastutil.showmes(firsttopActivity.this, "瞎几把乱充");
                }
            } else {
                Toast.makeText(firsttopActivity.this, "充值金额不能为零", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public List<String> getDataSource() {
        List<String> sqinnerList = new ArrayList<String>();
        sqinnerList.add("1");
        sqinnerList.add("2");
        sqinnerList.add("3");
        return sqinnerList;
    }

    private void adddatas(int number) {
        SQLiteDatabase adddata = dbHelper.getWritableDatabase();
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateNowStr = sdf.format(date);
        ContentValues values = new ContentValues();
        values.put("id", serial);
        values.put("carid", keyid);
        values.put("manoy", number);
        values.put("username", "admin");
        values.put("time", dateNowStr);
        long num = adddata.insert("CAR", null, values);
        Log.d("添加成功", "成功了" + num);
        values.clear();
    }

    private void getdataserial() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("CAR", null, null, null, null, null, null);
        serial = cursor.getCount();
    }

    public void getjson() {
        Log.d("进来了", "进来了");
        new Thread(new Runnable() {

            @Override
            public void run() {
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                MediaType mediaType = MediaType.parse("application/json");
                Map map = new HashMap<>();
                map.put("CarId", keyid);
                map.put("UserName", "user1");
                String parms = gson.toJson(map);
                RequestBody requestBody = RequestBody.create(mediaType, parms);
                Request request = new Request.Builder().post(requestBody).url(urljson).build();
                try {
                    okHttpClient = new OkHttpClient();
                    Response response = okHttpClient.newCall(request).execute();
                    final String resuit = response.body().string();
                    Log.d("获取的json的是", resuit + "");
                    final String num = getStringjson(resuit);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_sql.setText(num);
                        }
                    });
                    response.body().close();
                } catch (IOException e) {
                    Log.d("IOException", "IOException");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getStringjson(String resuit) {
        Log.d("进来了解析json", "开始解析");
        Gson gson = new Gson();
        app appList = gson.fromJson(resuit, app.class);
        Log.d("得到的余额为", appList.getBalance() + "");
        return appList.getBalance();

    }

    private void RechargeBalance() {
        Log.d("进入到充值入口", "正在充值中");
        Log.d("车号为", "" + keyid);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");
                    Map map = new HashMap<>();
                    map.put("CarId", keyid);
                    map.put("Money", numberrech);
                    map.put("UserName", "user1");
                    String parms = gson.toJson(map);
                    Log.d("上传的json", parms);
                    RequestBody requestBody = RequestBody.create(mediaType, parms);
                    Request requestset = new Request.Builder().post(requestBody).url(urlset).build();
                    okHttpClient.newCall(requestset).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("充值成功", "充值成功了");

            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    class OnClick implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.tv_myaccount:
                    Log.d("开始页面的跳转", "开始了");
                    mpop.dismiss();
                    intent = new Intent(firsttopActivity.this, firsttopActivity.class);
                    break;
                case R.id.tv_redgrenn:
                    mpop.dismiss();
                    intent = new Intent(firsttopActivity.this, twotopActivity.class);
                    break;
                case R.id.tv_zhangdan:
                    mpop.dismiss();
                    intent = new Intent(firsttopActivity.this, TherrtopActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
