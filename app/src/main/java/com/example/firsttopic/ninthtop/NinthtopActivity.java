package com.example.firsttopic.ninthtop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.therrtop.TherrtopActivity;

public class NinthtopActivity extends MyAppCompatActivity implements View.OnClickListener {
    private View view;
    private TextView alltextView, mrecordtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        Menu menu = super.setMenu(this, "账号管理", R.layout.layput_ringht_title);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));

        try {
            view = menu.getLinear_main_more();

        } catch (Exception e) {
            e.printStackTrace();
        }
        alltextView = view.findViewById(R.id.tv_Rechargeall);
        mrecordtext = view.findViewById(R.id.tv_redgrenn);
//        mrecordtext.setOnClickListener(this)

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_redgrenn:
                Intent intent = new Intent(NinthtopActivity.this, TherrtopActivity.class);
                startActivity(intent);
                break;
        }
    }

}
