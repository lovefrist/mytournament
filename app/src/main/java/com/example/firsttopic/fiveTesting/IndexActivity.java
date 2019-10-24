package com.example.firsttopic.fiveTesting;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;

public class IndexActivity extends MyAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Menu menu = super.setMenu(this, "环境指标", null);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255, 255, 255));

    }
}
