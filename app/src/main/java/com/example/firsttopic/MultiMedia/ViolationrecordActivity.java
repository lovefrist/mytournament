package com.example.firsttopic.MultiMedia;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;

import java.util.ArrayList;
import java.util.List;


public class ViolationrecordActivity extends MyAppCompatActivity {
    private RadioButton mrgmp4, mrgjpg;
    private RecyclerView mrvclermp4, mrvclerjpg;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violationrecord);
        Log.d("进来了违章查询页面", "yes");
        Menu menu = super.setMenu(this, "车辆违章", null);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
        mrvclermp4 = findViewById(R.id.rv_mp4data);
        mrvclerjpg = findViewById(R.id.rv_jpgdata);
        mrgmp4 = findViewById(R.id.rb_btnmp4);
        mrgjpg = findViewById(R.id.rb_btrjpg);
        mrgjpg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    mrvclermp4.setVisibility(View.VISIBLE);
                    mrvclerjpg.setVisibility(View.GONE);
                } else {
                    mrvclermp4.setVisibility(View.GONE);
                    mrvclerjpg.setVisibility(View.VISIBLE);
                }
            }
        });
        mrgmp4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mrvclermp4.setVisibility(View.VISIBLE);
                    mrvclerjpg.setVisibility(View.GONE);
                } else {
                    mrvclermp4.setVisibility(View.GONE);
                    mrvclerjpg.setVisibility(View.VISIBLE);
                }
            }
        });

        mrvclermp4.setLayoutManager(new GridLayoutManager(this, 4));
        Mp4Adapter mp4Adapter = new Mp4Adapter(ViolationrecordActivity.this, getTextlist(), getmp4int());

        mrvclermp4.setAdapter(mp4Adapter);

        mrvclerjpg.setLayoutManager(new GridLayoutManager(ViolationrecordActivity.this,4));
        JpgsetAdapter jpgsetAdapter = new JpgsetAdapter(ViolationrecordActivity.this,getImgres());
        mrvclerjpg.setAdapter(jpgsetAdapter);

    }
    private ArrayList<Integer> getmp4int(){
       ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(R.raw.mp401);
        integerList.add(R.raw.mp402);
        integerList.add(R.raw.mp403);
        integerList.add(R.raw.mp404);
        integerList.add(R.raw.mp405);
        integerList.add(R.raw.mp406);
        integerList.add(R.raw.mp407);
        integerList.add(R.raw.mp408);
        return integerList;
    }
    private List<String> getTextlist(){

        List<String> imglist = new ArrayList<>();
        imglist.add("宝马汽车配置");
        imglist.add("车辆管理");
        imglist.add("红路灯管理");
        imglist.add("跑车宣传");
        imglist.add("汽车历史教育");
        imglist.add("汽车历史视频");
        imglist.add("我的座驾");
        imglist.add("智能交通");
        return imglist;
    }
    private ArrayList<Integer> getImgres(){

        ArrayList<Integer> intjpg = new ArrayList<>();
        intjpg.add(R.drawable.jpg01);
        intjpg.add(R.drawable.jpg02);
        intjpg.add(R.drawable.jpg03);
        intjpg.add(R.drawable.jpg04);
        intjpg.add(R.drawable.jpg05);
        intjpg.add(R.drawable.jpg06);
        intjpg.add(R.drawable.jpg07);
        intjpg.add(R.drawable.jpg08);
        return intjpg;
    }

}
