package com.example.firsttopic.therrtop;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.GetSetfile.MyDatabaseHelper;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.firsttop.firsttopActivity;
import com.example.firsttopic.twotop.twotopActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TherrtopActivity extends MyAppCompatActivity {
    private ImageView imageView;
    private PopupWindow mpop;
    private Spinner msptherr;
    private RecyclerView mrecyclerView;
    private int spidkey=0;
    private MyDatabaseHelper dbHelper;
    private Cursor cursor;
    private Button mtherrbtn;
    private List<SetGeneric> lists;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therrtop);
        super.setMenu(this,"账单管理",null);
        lists = new ArrayList<>();
        dbHelper = new MyDatabaseHelper(this,"CARRecharge",null,7);
        getdatadaptet();


        msptherr = findViewById(R.id.sp_therrsp);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(TherrtopActivity.this,R.layout.textfiast,getdatastring());
        adapter.setDropDownViewResource(R.layout.breakcles);
        msptherr.setAdapter(adapter);
        msptherr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spidkey = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mrecyclerView =findViewById(R.id.rv_ther);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(TherrtopActivity.this));
        if (cursor.moveToFirst()){

            SetTherrAdapter adapter1 = new SetTherrAdapter(TherrtopActivity.this,lists);
            mrecyclerView.setAdapter(adapter1);
        }


        mtherrbtn = findViewById(R.id.btn_therrbtInquire);
        mtherrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setsort();
            }
        });

    }
    private List<String> getdatastring(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("时间升序");
        arrayList.add("时间降序");
        return arrayList;
    }

    private void setsort(){
        switch (spidkey){
            case 1:
                Collections.sort(lists, new Comparator<SetGeneric>() {
                    @Override
                    public int compare(SetGeneric o1, SetGeneric o2) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                     try {
                         Date dt1 = format.parse(o1.getTime());
                         Date dt2 = format.parse(o2.getTime());
                         if (dt1.getTime()>dt2.getTime()){
                            return 1;
                         }else return -1;
                     }catch (Exception e){
                         Log.d("时间出现异常", "compare: 他真的出现了");
                         e.printStackTrace();
                     }
                       return 0;
                    }
                });
                break;
            case 2:
                Collections.sort(lists, new Comparator<SetGeneric>() {
                    @Override
                    public int compare(SetGeneric o1, SetGeneric o2) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        try {
                            Date dt1 = format.parse(o1.getTime());
                            Date dt2 = format.parse(o2.getTime());
                            if (dt1.getTime()<dt2.getTime()){
                                return 1;
                            }else return -1;
                        }catch (Exception e){
                            Log.d("时间出现异常", "compare: 他真的出现了");
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                break;
        }
        SetTherrAdapter adapter1 = new SetTherrAdapter(TherrtopActivity.this,lists);
        mrecyclerView.setAdapter(adapter1);
    }
    private void getdatadaptet(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cursor = db.query("CAR",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            for (int i = 0; i < cursor.getCount(); i++) {
                SetGeneric setGeneric = new SetGeneric();
                setGeneric.setId(cursor.getInt(0));
                setGeneric.setCarid(cursor.getString(1));
                setGeneric.setMoney(cursor.getString(2));
                setGeneric.setPopel(cursor.getString(3));
                setGeneric.setTime(cursor.getString(4));

                Log.d("", "getdatadaptet: "+cursor.getInt(0));
                Log.d("", "getdatadaptet: "+cursor.getString(1));
                Log.d("", "getdatadaptet: "+cursor.getString(2));
                Log.d("", "getdatadaptet: "+cursor.getString(3));
                Log.d("", "getdatadaptet: "+cursor.getString(4));
                cursor.moveToNext();
                lists.add(setGeneric);

            }

        }else {
            Log.d("", "getdatadaptet: Cursor NUll");
        }


    }




    class OnClick implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.tv_myaccount:
                    Log.d("开始页面的跳转","开始了");
                    mpop.dismiss();
                    intent = new Intent(TherrtopActivity.this, firsttopActivity.class);
                    break;
                case R.id.tv_redgrenn:
                    mpop.dismiss();
                    intent = new Intent(TherrtopActivity.this, twotopActivity.class);
                    break;
                case R.id.tv_zhangdan:
                    mpop.dismiss();
                    intent = new Intent(TherrtopActivity.this, TherrtopActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

}
