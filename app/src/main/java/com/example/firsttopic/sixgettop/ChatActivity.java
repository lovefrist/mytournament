package com.example.firsttopic.sixgettop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.fiveTesting.MyfiveDatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends MyAppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ArrayList<TextView> textlit;
    private TextView tv_temperature, tv_humidity, tv_LightIntensity, tv_pm25, tv_co2, tv_status;
    public MyfiveDatabaseHelper dbHelper;
    private ArrayList<View> arrayList;
    private LineChart lineChart,mLineChar;
    private ArrayList<Entry> values;
    private LineDataSet set1;
    private MyfiveDatabaseHelper dbHoder;
    private List<Integer> temlist;
    private List<String>  datalist;
    private Handler handler = new Handler();
    private  boolean swut = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Menu menu = super.setMenu(this, "指标图", null);
        menu.needmove = true;
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));
        dbHoder = new MyfiveDatabaseHelper(this,"indexsetlis",null,9);
        Intent intent = getIntent();
        viewPager = findViewById(R.id.vp_Chart);
        temlist = new ArrayList<>();
        datalist = new ArrayList<>();
        MyChartadapt myChartadapt = new MyChartadapt(getdata());
        viewPager.setAdapter(myChartadapt);
        tv_temperature = findViewById(R.id.tv_temperature);
        tv_temperature.setOnClickListener(this::onClick);
        tv_humidity = findViewById(R.id.tv_humidity);
        tv_humidity.setOnClickListener(this::onClick);
        tv_LightIntensity = findViewById(R.id.tv_LightIntensity);
        tv_LightIntensity.setOnClickListener(this::onClick);
        tv_pm25 = findViewById(R.id.tv_pm25);
        tv_pm25.setOnClickListener(this::onClick);
        tv_co2 = findViewById(R.id.tv_co2);
        tv_co2.setOnClickListener(this);
        tv_status = findViewById(R.id.tv_status);
        tv_status.setOnClickListener(this::onClick);
        textlit = new ArrayList<>();
        textlit.add(tv_temperature);
        textlit.add(tv_humidity);
        textlit.add(tv_LightIntensity);
        textlit.add(tv_pm25);
        textlit.add(tv_co2);
        textlit.add(tv_status);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { //检测view的滑动
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动到第几个

                setitme(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Log.d(TAG, "onCreate:在Chat Actity" + viewPager.getCurrentItem());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (swut){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getSQldata(0);
                            getSQldata(1);
                            getSQldata(2);
                            getSQldata(3);
                            getSQldata(4);
                            getSQldata(5);
                        }
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    private ArrayList<View> getdata(){
         arrayList = new ArrayList<>();
         for (int i =0;i<6;i++){
             arrayList.add(View.inflate(this,R.layout.layout_chart,null));
         }
        return arrayList;
    }

    private void setitme(int pos) {
       TextView textView =  arrayList.get(pos).findViewById(R.id.tv_titlechart);


        switch (pos){
            case 0:
                Log.d(TAG, "setitme: 设置温度页面");
                textView.setText("温度");
                break;
            case 1:
                Log.d(TAG, "setitme: 设置湿度页面");
                textView.setText("湿度");
                break;
            case 2:
                textView.setText("光照");

                break;
            case 3:
                textView.setText("co2");

                break;
            case 4:
                textView.setText("pm2.5");
                break;
            case 5:
                textView.setText("路口状况");
                break;
        }

        for (int i = 0; i < textlit.size(); i++) {
            if (i == pos)
                textlit.get(i).setBackgroundResource(R.drawable.yuansixhui);
            else
                textlit.get(i).setBackgroundResource(R.drawable.yuansix);

        }
    }
    private void getSQldata(int pos){
        lineChart = arrayList.get(pos).findViewById(R.id.lc_chart);
        mLineChar = lineChart;
        Log.d(TAG, "getSQldata: 连接数据库");
        SQLiteDatabase db = dbHoder.getWritableDatabase();
       Cursor cursor = db.query("setindex",null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            Log.d(TAG, "getSQldata: 内容"+cursor.getString(cursor.getColumnIndex("temperature")));
            do {
                switch (pos){
                    case 0:

                        temlist.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("temperature"))));
                        datalist.add(cursor.getString(cursor.getColumnIndex("data")));
                    break;
                    case 1:
                        temlist.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("humidity"))));
                        datalist.add(cursor.getString(cursor.getColumnIndex("data")));
                        break;
                    case 2:
                        temlist.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("LightIntensity"))));
                        datalist.add(cursor.getString(cursor.getColumnIndex("data")));

                        break;
                    case 3:
                        temlist.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("co2"))));
                        datalist.add(cursor.getString(cursor.getColumnIndex("data")));
                        break;
                    case 4:
                        temlist.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("pm25"))));
                        datalist.add(cursor.getString(cursor.getColumnIndex("data")));
                        break;
                    case 5:
                        temlist.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("status"))));
                        datalist.add(cursor.getString(cursor.getColumnIndex("data")));
                        break;

                }
            }while (cursor.moveToNext());
        }
        values = new ArrayList<Entry>(); /*存储渲染LineChart 数据*/
        for (int j = 0;j<datalist.size();j++){
            values.add(new Entry(j*3, temlist.get(j), datalist.get(j)));
    }
        initChart();
        setLineChartData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_temperature:
                viewPager.setCurrentItem(0);
                tv_temperature.setBackgroundResource(R.drawable.yuansixhui);
                tv_humidity.setBackgroundResource(R.drawable.yuansix);
                tv_LightIntensity.setBackgroundResource(R.drawable.yuansix);
                tv_co2.setBackgroundResource(R.drawable.yuansix);
                tv_pm25.setBackgroundResource(R.drawable.yuansix);
                tv_status.setBackgroundResource(R.drawable.yuansix);

                break;
            case R.id.tv_humidity:
                viewPager.setCurrentItem(1);
                tv_temperature.setBackgroundResource(R.drawable.yuansix);
                tv_humidity.setBackgroundResource(R.drawable.yuansixhui);
                tv_LightIntensity.setBackgroundResource(R.drawable.yuansix);
                tv_co2.setBackgroundResource(R.drawable.yuansix);
                tv_pm25.setBackgroundResource(R.drawable.yuansix);
                tv_status.setBackgroundResource(R.drawable.yuansix);

                break;
            case R.id.tv_LightIntensity:
                viewPager.setCurrentItem(2);

                tv_temperature.setBackgroundResource(R.drawable.yuansix);
                tv_humidity.setBackgroundResource(R.drawable.yuansix);
                tv_LightIntensity.setBackgroundResource(R.drawable.yuansixhui);
                tv_co2.setBackgroundResource(R.drawable.yuansix);
                tv_pm25.setBackgroundResource(R.drawable.yuansix);
                tv_status.setBackgroundResource(R.drawable.yuansix);

                break;
            case R.id.tv_pm25:
                viewPager.setCurrentItem(3);
                tv_temperature.setBackgroundResource(R.drawable.yuansix);
                tv_humidity.setBackgroundResource(R.drawable.yuansix);
                tv_LightIntensity.setBackgroundResource(R.drawable.yuansix);
                tv_co2.setBackgroundResource(R.drawable.yuansix);
                tv_pm25.setBackgroundResource(R.drawable.yuansixhui);
                tv_status.setBackgroundResource(R.drawable.yuansix);
                break;
            case R.id.tv_co2:
                viewPager.setCurrentItem(4);
                tv_temperature.setBackgroundResource(R.drawable.yuansix);
                tv_humidity.setBackgroundResource(R.drawable.yuansix);
                tv_LightIntensity.setBackgroundResource(R.drawable.yuansix);
                tv_co2.setBackgroundResource(R.drawable.yuansixhui);
                tv_pm25.setBackgroundResource(R.drawable.yuansix);
                tv_status.setBackgroundResource(R.drawable.yuansix);
                break;
            case R.id.tv_status:
                viewPager.setCurrentItem(5);
                tv_temperature.setBackgroundResource(R.drawable.yuansix);
                tv_humidity.setBackgroundResource(R.drawable.yuansix);
                tv_LightIntensity.setBackgroundResource(R.drawable.yuansix);
                tv_co2.setBackgroundResource(R.drawable.yuansix);
                tv_pm25.setBackgroundResource(R.drawable.yuansix);
                tv_status.setBackgroundResource(R.drawable.yuansixhui);

                break;
        }
    }
    private void setLineChartData() {

        datalist.clear();
        temlist.clear();
        setData(values);
    }

    private void setData(ArrayList<Entry> values) {
        if (mLineChar.getData() != null && mLineChar.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChar.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChar.getData().notifyDataChanged();
            mLineChar.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, null);
            // 在这里设置线
            //set1.enableDashedLine(10f, 5f, 0f);  //虚线
            //set1.enableDashedHighlightLine(10f, 5f, 0f);//虚线
            set1.setCircleColor(Color.LTGRAY); //圆圈 顶部的颜色
            set1.setLineWidth(2);      //设置 线条粗细
            set1.setColor(Color.LTGRAY); //线条颜色
            set1.setDrawCircleHole(false);
            set1.setCircleRadius(10);
            set1.setValueTextColor(Color.BLUE);
            set1.setFormLineWidth(1);
            set1.setDrawValues(true);   //不现实节点文本
            set1.setValueTextSize(9f);  //设置文本显示大小
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            //添加数据集
            dataSets.add(set1);
            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);
            //谁知数据
            mLineChar.setData(data);
        }
    }

    /**
     * 初始化线性图
     */
    private void initChart() {
        lineChart.setLogEnabled(false);                 /* 设置为true将激活chart的logcat输出。但这不利于性能，如果不是必要的，应保持禁用*/
        lineChart.setDescription(null);                 /* 设置图表的描述文字*/
        lineChart.getLegend().setEnabled(false);        /*设置X轴下方的图表样式无*/
        lineChart.setDrawBorders(false);                /* 启用/禁用绘制图表边框（chart周围的线）*/
        //lineChart.setScaleEnabled(false);               /* 启用/禁用缩放图表上的两个轴 */
        lineChart.setDoubleTapToZoomEnabled(false);     /* 设置为false以禁止通过在其上双击缩放图表*/
        lineChart.setTouchEnabled(false);               /* 设置图表不可触摸 可以让坐标线消失*/
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
//        lineChart.animateX(2000);
        /*------------------------------------------------------万恶的分割线 设置X轴-------------------------------------------------------------------*/
        XAxis xAxis = lineChart.getXAxis();             /* 获取 表的 X轴*/
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  /* 设置 X轴 位置 在底部显示*/
        xAxis.setAxisLineWidth(1);                      /* 设置 X轴 显示宽度*/
        xAxis.setAxisMinimum(3);                        /* 设置 X轴 初始值*/
        //xAxis.setAxisMaximum(0);                    /* 设置 X轴 最大值*/
        // xAxis.setDrawAxisLine(true);                    /* 设置 X轴 轴线 是否显示*/
        xAxis.setLabelRotationAngle(90f);
        xAxis.setDrawGridLines(false);                  /* 设置 X轴 表格线 不显示 */
        xAxis.setEnabled(true);                         /* 设置 X轴 是否显示*/
        Log.d(TAG, "initChart: "+datalist.size());
        xAxis.setLabelCount(datalist.size());                        /* 设置 X轴显示的个数*/
        xAxis.setAvoidFirstLastClipping(false);          /*图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘*/
        xAxis.setDrawLabels(true);


        xAxis.setValueFormatter(new ValueFormatter() {//自定义轴内容
            @Override
            public String getFormattedValue(float value) {
//                Log.e("GG", "getFormattedValue: " + value);
                return values.get((int) value / 3).getData() + "";
            }
        });

//        // xAxis.setValueFormatter(new T(mValues));
        /*------------------------------------------------------万恶的分割线 设置Y轴 Left-------------------------------------------------------------------*/
        YAxis LyAxis = lineChart.getAxisLeft();           /* 获得 最左边的Y轴*/
        LyAxis.setDrawGridLines(true);                   /* 设置 Y轴表格线 不显示 */
        LyAxis.setDrawAxisLine(true);                     /* 设置 Y轴轴线 是否显示*/
        LyAxis.setEnabled(true);                          /* 设置 Y轴 是否显示*/
        LyAxis.setAxisLineWidth(1);                       /* 设置 Y轴 显示宽度*/
        //LyAxis.setAxisMinimum(0);                         /* 设置 Y轴 初始值*/
        //LyAxis.setAxisMaximum(100);                       /* 设置 Y轴 最大值*/
        /*------------------------------------------------------万恶的分割线 设置Y轴 Left-------------------------------------------------------------------*/
        YAxis RyAxis = lineChart.getAxisRight();          /* 获得 最左边的Y轴*/
        RyAxis.setDrawGridLines(false);                   /* 设置 Y轴表格线 不显示 */
        RyAxis.setDrawAxisLine(true);                     /* 设置 Y轴轴线 是否显示*/
        RyAxis.setEnabled(false);                         /* 设置 Y轴R 是否显示*/
        RyAxis.setAxisLineWidth(1);                       /* 设置 Y轴 显示宽度*/
        //RyAxis.setAxisMinimum(0);                         /* 设置 Y轴 初始值*/
        //RyAxis.setAxisMaximum(100);                      /* 设置 Y轴 最大值*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        swut = false;
    }
}
