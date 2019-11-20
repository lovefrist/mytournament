package com.example.firsttopic;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firsttopic.GetSetfile.MyDatabaseHelper;
import com.example.firsttopic.mysqlConnect.Adddata;
import com.example.firsttopic.mysqlConnect.DBUtils;

import java.util.HashMap;

public class LongTextActivity extends AppCompatActivity {
    private CheckBox checkBox, mPreservation;
    private EditText mPasswordEdit, mUserEdit;
    private String TAG = "开始监听";
    private MyDatabaseHelper dbHelper;
    private Button mbtnLand, mbtnregister;
    private SharedPreferences pref;
    private boolean pass_flags;
    private boolean baocun;
    private Handler handler = new Handler();
    private AlertDialog  alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_text);

        pref = getSharedPreferences("data", MODE_PRIVATE);
        baocun = pref.getBoolean("state", true);
        baocun = pref.getBoolean("state", false);


        Log.d(TAG, "onCreate: " + baocun);
        dbHelper = new MyDatabaseHelper(this, "userget", null, 1);
        mPasswordEdit = findViewById(R.id.paswrod);
        checkBox = findViewById(R.id.Retain_cgeckbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPasswordEdit.setTransformationMethod(null);
                    Toast toast = Toast.makeText(LongTextActivity.this, null, Toast.LENGTH_SHORT);
                    toast.setText("选中了");
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(LongTextActivity.this, null, Toast.LENGTH_SHORT);
                    toast.setText("没有选中了");
                    toast.show();
                    mPasswordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mUserEdit = findViewById(R.id.btn_Accoun);

        mPreservation = findViewById(R.id.Preservation_Eitle);
        mPreservation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, "onCheckedChanged: 开始保存密码");
                    Toast toast = Toast.makeText(LongTextActivity.this, null, Toast.LENGTH_SHORT);
                    toast.setText("保存密码");
                    toast.show();


                    setdatas(isChecked);
                } else {
                    Toast toast = Toast.makeText(LongTextActivity.this, null, Toast.LENGTH_SHORT);
                    toast.setText("不保存密码");
                    toast.show();
                    setdatas(isChecked);
                }
            }
        });
        if (pref.getBoolean("state", true)) {
            Log.d(TAG, "onCreate: 添加");
            getdata();
            mPreservation.setChecked(true);
        } else {
            Log.d(TAG, "onCreate: 清空");
            pandu();
        }

        mbtnLand = findViewById(R.id.btn_Long);
        mbtnLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + mPasswordEdit.getText());

                getmysql();


            }
        });
        mbtnregister = findViewById(R.id.btn_Register);
        mbtnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 开始添加账号");

                setadduser();
            }
        });

    }

    private void setWrite() {
        SQLiteDatabase inset = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", mUserEdit.getText() + "");
        values.put("posswd", mPasswordEdit.getText() + "");
        long num = inset.insert("User", null, values);
        Log.d(TAG, "setWrite: 数据条数" + num);
        values.clear();
    }

    private void setUserValue() {
        SQLiteDatabase getval = dbHelper.getWritableDatabase();
        Cursor cursor = getval.query("User", null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            mUserEdit.setText(cursor.getString(0));
            mPasswordEdit.setText(cursor.getString(1));
        }
    }

    private void pandu() {
        if (pref.getBoolean("state", false)) {
            mUserEdit.setText(null);
            mPasswordEdit.setText(null);
        }
    }
   private void setadduser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String user = mUserEdit.getText()+"";
                String powss = mPasswordEdit.getText()+"";
                Adddata.setadddata(user,powss);
            }
        }).start();



   }
    private void setdatas(boolean out) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("user", mUserEdit.getText() + "");
        editor.putString("powss", mPasswordEdit.getText() + "");
        Log.d("选中没有", "" + out);
        editor.putBoolean("state", out);
        editor.apply();
    }

    private void getdata() {

        mUserEdit.setText(pref.getString("user", ""));
        mPasswordEdit.setText(pref.getString("powss", ""));
    }

    private void getmysql() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        View view = LayoutInflater.from(LongTextActivity.this).inflate(R.layout.partext_layout,null);
                         alertDialog = new AlertDialog.Builder(LongTextActivity.this).create();
                        alertDialog.setView(view);
                        alertDialog.show();
                        final Window window = alertDialog.getWindow();
                        window.setBackgroundDrawable(new ColorDrawable(0));
                        alertDialog.setCanceledOnTouchOutside(false);
                    }
                });

                String data = mUserEdit.getText() + "";
                HashMap<String, Object> map = DBUtils.getInfoByName(data);
                Log.d(TAG, "run: " + map.toString());
                if (map.size() != 0) {
                    if (map.get("user").equals(mUserEdit.getText() + "") && map.get("password").equals(mPasswordEdit.getText() + "")) {
                        Log.d(TAG, "run: 登陆成功");
                        pass_flags = true;

                    } else {
                        pass_flags = false;
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (pass_flags) {

                            Intent intent = new Intent(LongTextActivity.this, MainActivity.class);
                            alertDialog.cancel();
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(LongTextActivity.this, null, Toast.LENGTH_SHORT);
                            Log.d(TAG, "onClick: " + mPasswordEdit.getText());
                            toast.setText("账号或者密码错误");
                            toast.setGravity(0, 20, 20);
                            toast.show();
                            alertDialog.cancel();
                        }
                    }
                });
            }
        }).start();


    }

}
