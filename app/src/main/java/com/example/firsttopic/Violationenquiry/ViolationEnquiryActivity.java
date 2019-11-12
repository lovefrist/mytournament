package com.example.firsttopic.Violationenquiry;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firsttopic.R;
import com.example.firsttopic.util.Toastutil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViolationEnquiryActivity extends AppCompatActivity {
    private String urlone = "http://192.168.3.5:8088/transportservice/action/GetCarPeccancy.do";
    private Button button;
    private EditText editText;
    private TextView textView;
    private OkHttpClient okHttpClient;
    private boolean result = false;
    private Handler handler =new Handler();
    private  String TAG = "ViolationEnquiryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_enquiry);
        button = findViewById(R.id.btn_caridquery);
        editText = findViewById(R.id.et_carid);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && s.toString().equals(".") && count == 1) {
                    //输入的第一个字符为"."
                    editText.setText("");
                } else if (s.length() >6 && count != 0) {
                    //当整数位数输入到达被要求的上限,并且当前在输入字符,而不是减少字符
                    editText.setText(s.subSequence(0, s.toString().length() - 1));
                    editText.setSelection(s.toString().length() - 1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        textView = findViewById(R.id.tv_error);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataJson();
            }
        });

    }

    private void dataJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    MediaType mediaType = MediaType.parse("application/json");
                    Map map = new HashMap<>();
                    Log.d(TAG, "run: 表单的值"+editText.getText().toString());
                    map.put("UserName", "user1");
                    map.put("carnumber","鲁"+editText.getText().toString());
                    String params = gson.toJson(map);
                    RequestBody requestBody = RequestBody.create(mediaType, params);
                    Request request = new Request.Builder().post(requestBody).url(urlone).build();
                    okHttpClient = new OkHttpClient();
                    Response response = null;
                    response = okHttpClient.newCall(request).execute();
                    final String resuit = response.body().string();

                    JSONObject jsonObject = new JSONObject(resuit);
                    String str="";
                    try {
                         str = jsonObject.getString("RESULT");
                    }catch (JSONException e){
                         str = jsonObject.getString("status");
                    }


                    if (str.equals("S")) {
                        result = true;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                       if (result){
                           Toastutil.showmes(ViolationEnquiryActivity.this,"详情违章结果");
                           Intent intent = new Intent(ViolationEnquiryActivity.this,ViolationDetailsActivity.class);
                           intent.putExtra("carid","鲁"+editText.getText().toString());
                           startActivity(intent);

                       }else {
                           textView.setText("没有查询到鲁"+editText.getText().toString()+"车的违章数据！");
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
}
