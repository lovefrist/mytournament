package com.example.firsttopic;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firsttopic.Fourtopic.ViolationrecordActivity;
import com.example.firsttopic.firsttop.firsttopActivity;
import com.example.firsttopic.therrtop.TherrtopActivity;
import com.example.firsttopic.twotop.twotopActivity;

public class MainActivity extends MyAppCompatActivity {
    private Button mfbutton, mtwobtn, mtherrbtn, mforbtn;

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
        setonck();

    }

    private void setonck() {
        OnClick onClick = new OnClick();
        mfbutton.setOnClickListener(onClick);
        mtwobtn.setOnClickListener(onClick);
        mtherrbtn.setOnClickListener(onClick);
        mforbtn.setOnClickListener(onClick);

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
            }
            startActivity(intent);
        }
    }
}
