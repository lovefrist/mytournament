package com.example.firsttopic.Feedback;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firsttopic.Menu;
import com.example.firsttopic.MyAppCompatActivity;
import com.example.firsttopic.R;
import com.example.firsttopic.util.Toastutil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedbackActivity extends MyAppCompatActivity {
        private TextView tvopinion;
        private View view;
        private EditText ettitle,etcontent,ettail;
        private Button btnupload;
        private MyopinionDatabaseHelper databaseHelper = new MyopinionDatabaseHelper(this,"opinion",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Menu menu = super.setMenu(this,"意见反馈",R.layout.left_layout);
        menu.getLinear_left().setBackgroundColor(Color.rgb(255,255,255));
        try {
           view = menu.getLinear_main_more();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvopinion = view.findViewById(R.id.tv_myopinion);
        setContentView(R.layout.activity_feedback);
        ettitle = findViewById(R.id.et_title);
        etcontent = findViewById(R.id.et_content);
        ettail = findViewById(R.id.et_tail);
        btnupload = findViewById(R.id.btn_upload);
        tvopinion.setOnClickListener(v -> {
            Intent intent =  new Intent(this, MyUploadActivity.class);
            startActivity(intent);
        });
        ettail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              

                if (start == 0 && !s.toString().equals("1") && count == 1) {
                    //输入的第一个字符不为"1"
                    ettail.setText("");
                }else if (s.length() ==2){
                    char[]  sa  = s.toString().toCharArray();
                    String str = sa[1]+"";
                    if (str.equals("2")||str.equals("0")){
                        ettail.setText(s.subSequence(0, s.toString().length() - 1));
                        ettail.setSelection(s.toString().length() - 1);
                    }
                }else if (s.length() >11 && count != 0) {
                    //当整数位数输入到达被要求的上限,并且当前在输入字符,而不是减少字符
                    ettail.setText(s.subSequence(0, s.toString().length() - 1));
                    ettail.setSelection(s.toString().length() - 1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etcontent.getText().toString().equals("")|ettitle.getText().toString().equals("")||ettail.getText().toString().equals("")||ettail.length()!=11){
                    Toastutil.showmes(FeedbackActivity.this,"提交失败因为内容不能为空或电话号码错误");
                }else {
                    Date date = new Date();
                    SimpleDateFormat dateFormat =   new SimpleDateFormat("yyyy-mm-dd MM-dd-ss ");
                    String time  =  dateFormat.format(date);
                    SQLiteDatabase database =  databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("title",ettitle.getText().toString());
                    values.put("content",etcontent.getText().toString());
                    values.put("time",time);
                    values.put("tail",ettail.getText().toString());
                    database.insert("indextall",null,values);
                    Toastutil.showmes(FeedbackActivity.this,"提交成功");
                }

            }
        });
    }
}
