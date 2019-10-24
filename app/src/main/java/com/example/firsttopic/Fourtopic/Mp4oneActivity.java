package com.example.firsttopic.Fourtopic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.firsttopic.R;

public class Mp4oneActivity extends AppCompatActivity {
private VideoView videoView;
private ImageView imageView;
private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int url = intent.getIntExtra("Url",0);
        String title = intent.getStringExtra("title");
        Log.d("传入的数值",""+url);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_mp4one);
        videoView = findViewById(R.id.vv_idcar);
        videoView.setVideoURI(Uri.parse("android.resource://com.example.firsttopic/" +url));
        videoView.setMediaController(new MediaController(this));
        imageView = findViewById(R.id.iv_backlft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mp4oneActivity.this,ViolationrecordActivity.class);
                startActivity(intent);
            }
        });
        textView = findViewById(R.id.tv_title);
        textView.setText(title);
    }
}
