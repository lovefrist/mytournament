package com.example.firsttopic;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SumGetJson {
    private String mgeturl;
    private int keyid;
    private OkHttpClient okHttpClient;
    private  String resuit;
    SumGetJson(String url,int i){
        mgeturl = url;
        keyid = i;
    }
    public String getjson() {
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
                Request request = new Request.Builder().post(requestBody).url(mgeturl).build();
                try {
                    okHttpClient = new OkHttpClient();
                    Response response = okHttpClient.newCall(request).execute();
                  resuit = response.body().string();
                    Log.d("获取的json的是", resuit + "");


                    response.body().close();
                } catch (IOException e) {
                    Log.d("IOException", "IOException");
                    e.printStackTrace();
                }
            }
        }).start();
return resuit;
    }

}
