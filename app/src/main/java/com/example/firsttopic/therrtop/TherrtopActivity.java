package com.example.firsttopic.therrtop;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.firsttopic.R;
import com.example.firsttopic.firsttop.firsttopActivity;
import com.example.firsttopic.twotop.twotopActivity;

public class TherrtopActivity extends Activity {
    private ImageView imageView;
    private PopupWindow mpop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therrtop);
        imageView = findViewById(R.id.iv_imgget);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popText = getLayoutInflater().inflate(R.layout.layout_navyes, null);

                TextView myaccount = popText.findViewById(R.id.tv_myaccount);
                TextView redgrenn = popText.findViewById(R.id.tv_redgrenn);
                TextView zhangdan = popText.findViewById(R.id.tv_zhangdan);
                  OnClick onClick =  new OnClick();
                zhangdan.setOnClickListener(onClick);
                myaccount.setOnClickListener(onClick);
                redgrenn.setOnClickListener(onClick);

                mpop = new PopupWindow(popText, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
                mpop.setOutsideTouchable(true);
                mpop.setFocusable(true);
                mpop.showAsDropDown(imageView);
            }
        });




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
