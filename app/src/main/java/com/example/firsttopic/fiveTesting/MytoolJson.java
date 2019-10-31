package com.example.firsttopic.fiveTesting;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.firsttopic.firsttop.app;
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

public class MytoolJson {
    private android.os.Handler handler = new Handler();
   private String urljson;
   private Context mcontext;
     private String resuit;
    public MytoolJson( String Json, Context context){

        urljson  =Json;
        mcontext = context;
    }
    public String getjson() {
        Log.d("进来了", "进来了");
        new Thread(new Runnable() {

            @Override
            public void run() {
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                MediaType mediaType = MediaType.parse("application/json");
                Map map = new HashMap<>();

                map.put("UserName", "user1");
                String parms = gson.toJson(map);
                RequestBody requestBody = RequestBody.create(mediaType, parms);
                Request request = new Request.Builder().post(requestBody).url(urljson).build();
                try {
                    OkHttpClient  okHttpClient = new OkHttpClient();
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
