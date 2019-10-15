package com.example.firsttopic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firsttopic.firsttop.firsttopActivity;

public class MainActivity extends AppCompatActivity {
    private Button mbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbutton = findViewById(R.id.btn_firsttopic);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, firsttopActivity.class);
                startActivity(intent);
            }
        });

    }
}
