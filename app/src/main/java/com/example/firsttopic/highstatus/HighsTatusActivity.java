package com.example.firsttopic.highstatus;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.mysqlConnect.Notice;

import java.util.List;
import java.util.Map;

public class HighsTatusActivity extends MyAppCompatActivity {
    private TextView tvroll;
    private RecyclerView recyclerView;
    private ViewFlipper mViewFlipper;
    private Handler handler = new Handler();
    private boolean allkey = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Menu menu =  super.setMenu(this,"高速路况",null);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255,255,255));
        setContentView(R.layout.activity_highs_tatus);
        recyclerView = findViewById(R.id.rv_information);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HighsTatusAdapter(this));
        mViewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
        mViewFlipper.setInAnimation(this, R.anim.up_in);
        mViewFlipper.setOutAnimation(this, R.anim.up_out);
        mViewFlipper.setFlipInterval(2000);
        setroll();
    }

    public TextView getTextView(String text){
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(50f);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
  private void   setroll(){
        new Thread(() -> {
            List<Map> listall = Notice.getInfoByName();
            handler.post(() -> {
                for(int j=0;j<listall.size();j++){
                    mViewFlipper.addView(getTextView(listall.get(j).get("content").toString()));
                }
                mViewFlipper.startFlipping();

            });


        }).start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        allkey = false;
    }
}
