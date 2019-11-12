package com.example.firsttopic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.EighthTops.EighthTopActivity;
import com.example.firsttopic.Fourtopic.ViolationrecordActivity;
import com.example.firsttopic.LightingManagement.LightManagActivity;
import com.example.firsttopic.RoadSituation.Road_SituationActivity;
import com.example.firsttopic.Violationenquiry.ViolationEnquiryActivity;
import com.example.firsttopic.firsttop.firsttopActivity;
import com.example.firsttopic.fiveTesting.IndexActivity;
import com.example.firsttopic.ninthtop.NinthtopActivity;
import com.example.firsttopic.seventhtop.SeventhTopActivity;
import com.example.firsttopic.sixgettop.ChatActivity;
import com.example.firsttopic.therrtop.TherrtopActivity;
import com.example.firsttopic.transportquery.TransportQueryActivity;
import com.example.firsttopic.twotop.twotopActivity;

public class MainActivity extends MyAppCompatActivity {
    private Button mfbutton, mtwobtn, mtherrbtn, mforbtn,mTasting,mChart,msevebtn,mgpbaibtn,mAccountbtn,mtan,mlemp,mbtnviolat,mbtnroad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Menu menu = super.setMenu(this, "首页", null);
        View view = menu.getLinear_left();
        view.setBackgroundColor(Color.rgb(255, 255, 255));
        mfbutton = findViewById(R.id.btn_firsttopic);
        mtwobtn = findViewById(R.id.btn_towtopic);
        mtherrbtn = findViewById(R.id.btn_therrtopic);
        mforbtn = findViewById(R.id.btnfourtopic);
        mTasting = findViewById(R.id.btn_Testing);
        mChart = findViewById(R.id.btn_Chart);
        msevebtn = findViewById(R.id.btn_sevefazhi);
        mgpbaibtn = findViewById(R.id.btn_gobai);
        mAccountbtn = findViewById(R.id.btn_Account);
        mtan = findViewById(R.id.btn_Tran);
        mlemp = findViewById(R.id.btn_lemp);
        mbtnviolat = findViewById(R.id.btn_Violat);
        mbtnroad = findViewById(R.id.btn_road);
        setonck();
    }

    private void setonck() {
        OnClick onClick = new OnClick();
        mfbutton.setOnClickListener(onClick);
        mtwobtn.setOnClickListener(onClick);
        mtherrbtn.setOnClickListener(onClick);
        mforbtn.setOnClickListener(onClick);
        mTasting.setOnClickListener(onClick);
        mChart.setOnClickListener(onClick);
        msevebtn.setOnClickListener(onClick);
        mgpbaibtn.setOnClickListener(onClick);
        mAccountbtn.setOnClickListener(onClick);
        mtan.setOnClickListener(onClick);
        mlemp.setOnClickListener(onClick);
        mbtnviolat.setOnClickListener(onClick);
        mbtnroad.setOnClickListener(onClick);
    }

    class OnClick implements RecyclerView.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_firsttopic:
                    intent = new Intent(MainActivity.this, firsttopActivity.class);
                    break;
                case R.id.btn_towtopic:
                    intent = new Intent(MainActivity.this, twotopActivity.class);
                    break;
                case R.id.btn_therrtopic:
                    intent = new Intent(MainActivity.this, TherrtopActivity.class);
                    break;
                case R.id.btnfourtopic:
                    intent = new Intent(MainActivity.this, ViolationrecordActivity.class);
                    break;
                case R.id.btn_Testing:
                    intent = new Intent(MainActivity.this, IndexActivity.class);
                    break;
                case R.id.btn_Chart:
                    intent = new Intent(MainActivity.this, ChatActivity.class);
                    break;
                case R.id.btn_sevefazhi:
                    intent = new Intent(MainActivity.this, SeventhTopActivity.class);
                    break;
                case R.id.btn_gobai:
                    intent = new Intent(MainActivity.this, EighthTopActivity.class);
                    break;
                case R.id.btn_Account:
                    intent = new Intent(MainActivity.this, NinthtopActivity.class);
                    break;
                case R.id.btn_Tran:
                    intent = new Intent(MainActivity.this, TransportQueryActivity.class);
                    break;
                case R.id.btn_lemp:
                    intent = new Intent(MainActivity.this, LightManagActivity.class);
                    break;
                case R.id.btn_Violat:
                    intent = new Intent(MainActivity.this, ViolationEnquiryActivity.class);
                    break;
                case R.id.btn_road:
                    intent = new Intent(MainActivity.this, Road_SituationActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
