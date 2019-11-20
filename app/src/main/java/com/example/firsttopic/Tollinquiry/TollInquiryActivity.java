package com.example.firsttopic.Tollinquiry;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;

import java.util.ArrayList;
import java.util.List;

public class TollInquiryActivity extends MyAppCompatActivity {
   private Spinner spyear,spmon,spday,spchangyear,spchangmon,spchangday;
   private RecyclerView recyclerView;
   private Button btnchargequery,btnSignout;

   private List<Integer> year,day,changeday;
   private DataData dateData,dataDatachang;

   private int overallyear,overallmon,overallday,overallchangeyear,overallchangemon,overallchangeday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setMenu(this,"收费查询",null);
        setContentView(R.layout.activity_toll_lnquiry);

        spyear = findViewById(R.id.sp_year);
        spmon = findViewById(R.id.sp_mon);
        spday = findViewById(R.id.sp_day);
        spchangyear = findViewById(R.id.sp_changeyear);
        spchangmon = findViewById(R.id.sp_changemon);
        spchangday = findViewById(R.id.sp_changday);
        recyclerView = findViewById(R.id.rv_translate);
        btnchargequery = findViewById(R.id.btn_chargequery);
        btnSignout = findViewById(R.id.btn_Signout);

        year = new ArrayList<Integer>();//给列表分配空间
        for (int i = 1949; i <= 2050; i++) {//把年份的选项加入year列表中；
            year.add(i);
        }


       ArrayAdapter yearadapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, year);//把年份的列表添加到适配器中 还有他的下拉选项
        yearadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//
        spyear.setAdapter(yearadapter);//把布局加入适配器 这边我们用默认布局
        spchangyear.setAdapter(yearadapter);
        List<Integer>  month = new ArrayList<Integer>();//给month列表分配空间
        for (int i = 1; i <= 12; i++) {
            month.add(i);//给month添加数据 1-12个月
        }
        //接下来和上面年份的一样
        ArrayAdapter  monthadapter = new ArrayAdapter<Integer>(TollInquiryActivity.this, android.R.layout.simple_spinner_item, month);
        monthadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                overallyear = year.get(position);
                dateData = new DataData( year.get(position));//给年份处理的对象分配空间
                spmon.setAdapter(monthadapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       spchangyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               overallchangeyear = year.get(position);
               dataDatachang = new DataData(year.get(position));
               spchangmon.setAdapter(monthadapter);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        spmon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int theday =  dateData.getMonth(position);
                Log.d(TAG, "onItemSelected:多少天"+theday);
                 day = new ArrayList<Integer>();
                for (int i = 1; i <= theday; i++) {
                    day.add(i);
                }
                Log.d(TAG, "onItemSelected: 大小"+day.size());
                ArrayAdapter  datadapter = new ArrayAdapter<Integer>(TollInquiryActivity.this, android.R.layout.simple_spinner_item, day);
                datadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spday.setAdapter(datadapter);
                overallmon = month.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spchangmon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int theday =  dataDatachang.getMonth(position);
                Log.d(TAG, "onItemSelected:多少天"+theday);
                changeday = new ArrayList<Integer>();
                for (int i = 1; i <= theday; i++) {
                    changeday.add(i);
                }
                Log.d(TAG, "onItemSelected: 大小"+changeday.size());
                ArrayAdapter  datadapter = new ArrayAdapter<Integer>(TollInquiryActivity.this, android.R.layout.simple_spinner_item, day);
                datadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spchangday.setAdapter(datadapter);
                overallchangemon = month.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                overallday = day.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spchangday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                overallchangeday = changeday.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
