package com.example.firsttopic.newsmedia;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;

import java.util.ArrayList;
import java.util.List;

public class NewsMediaActivity extends MyAppCompatActivity {
    private View view1,view2,view3;
    private TextView tvtechnology,tveducation,tvsports;
    private List<View> listview = new ArrayList();
    private List<TextView> tvlist = new ArrayList();
    private ListView listViewas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setMenu(this,"新闻查询",null);
        setContentView(R.layout.activity_news_media);
        view1 = findViewById(R.id.vie_v1);
        view2 = findViewById(R.id.vie_v2);
        view3 = findViewById(R.id.vie_v3);
        listview.add(view1);
        listview.add(view2);
        listview.add(view3);
        tvtechnology = findViewById(R.id.tv_technology);
        tveducation = findViewById(R.id.tv_education);
        tvsports = findViewById(R.id.tv_sports);
        tvlist.add(tvtechnology);
        tvlist.add(tveducation);
        tvlist.add(tvsports);
        for (int i=0;i<tvlist.size();i++){
            int find = i;
            tvlist.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeColor(find);
                }
            });
        }
        listViewas = findViewById(R.id.rv_media);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.listview_adapter,getstr(1));
        listViewas.setAdapter(arrayAdapter);

    }
   private void   changeColor(int  index){
        for (int i=0;i<listview.size();i++ ){
            if (i == index){
                listview.get(i).setBackgroundColor(Color.rgb(252,236,128));
            }else {
                listview.get(i).setBackgroundColor(Color.rgb(0,0,0));
            }
        }
    }
    private String[] getstr(int i){
        String[] data = new String[]{
          "sda","asd"
        };
        return data;
    }
}
