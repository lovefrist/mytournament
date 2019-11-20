package com.example.firsttopic.EighthTops;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EighthTopActivity extends MyAppCompatActivity {
    private TextView textView, mtvchux;
    private List<RadioButton> rblist;
    private int daynum;
    private List<TextView> tvcarlist;
    private Handler handler = new Handler();
    private final String TAG = "EightTOP";
    private List<ToggleButton> sclist;
    Calendar calendar = Calendar.getInstance(Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eighth_top);
        Menu menu = super.setMenu(this, "出行管理", null);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
        rblist = new ArrayList<>();
        rblist.add(findViewById(R.id.rb_red));
        rblist.add(findViewById(R.id.rb_green));
        rblist.add(findViewById(R.id.rb_Yellow));

        sclist = new ArrayList<>();
        sclist.add(findViewById(R.id.tb_fist));
        sclist.add(findViewById(R.id.tb_two));
        sclist.add(findViewById(R.id.tb_therr));

        tvcarlist = new ArrayList<>();
        tvcarlist.add(findViewById(R.id.tv_car1));
        tvcarlist.add(findViewById(R.id.tv_car2));
        tvcarlist.add(findViewById(R.id.tv_car3));
        for (int i= 0 ;i<tvcarlist.size();i++){
            int fini = i+1;
            sclist.get(i).setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked){
                    tvcarlist.get(fini-1).setText(""+fini+"号");
                    tvcarlist.get(fini-1).setVisibility(View.VISIBLE);
                }else {
                    tvcarlist.get(fini-1).setText("");
                    tvcarlist.get(fini-1).setVisibility(View.GONE);
                }
            });
        }

        textView = findViewById(R.id.tv_Texttime);
        mtvchux = findViewById(R.id.tv_chuxing);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd天");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
        String dateNowStr = sdf.format(date);
        daynum = Integer.parseInt(sdf2.format(date));
        textView.setText(dateNowStr);
        settime();
        textView.setOnClickListener((View v) -> {
            showDatePickerDialog(EighthTopActivity.this, 4, textView, calendar);
        });
        charcorl();
    }

    private void settime() {
        int number = daynum % 2;
        if (number == 1) {
            mtvchux.setText("单号出行的车俩为");
        } else {
            mtvchux.setText("双号出行的车俩为");
        }
        setscCheck(daynum);
    }

    public void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        Log.d(TAG, "showDatePickerDialog: 开始选择时间 ");
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        // 绑定监听器(How the parent is notified that the date is set.)
        new DatePickerDialog(activity, themeResId, (view, year, monthOfYear, dayOfMonth) -> {
            // 此处得到选择的时间，可以进行你想要的操作

            tv.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
            daynum = dayOfMonth;
            settime();
            setscCheck(dayOfMonth);
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void charcorl() {
        new Thread(() -> {
            while (true) {
                for (int i = 0; i < rblist.size(); i++) {
                    int finid = i;
                    handler.post(() -> {
                        setColr(finid);
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void setColr(int i) {
        rblist.get(i).setChecked(true);
    }

    private void setscCheck(int num){
        if (num%2 ==1){
            sclist.get(0).setChecked(true);
            sclist.get(0).setEnabled(true);
            sclist.get(1).setChecked(false);
            sclist.get(1).setEnabled(false);
            sclist.get(2).setChecked(true);
            sclist.get(2).setEnabled(true);
        }else {
            sclist.get(0).setChecked(false);
            sclist.get(0).setEnabled(false);
            sclist.get(1).setChecked(true);
            sclist.get(1).setEnabled(true);
            sclist.get(2).setChecked(false);
            sclist.get(2).setEnabled(false);
        }
    }
}
