package com.example.firsttopic.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Toastutil {
    public  static Toast mtoast;
    public static void showmes(Context context,String msg){
        if (mtoast == null ){
            mtoast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }else {
            mtoast.setText(msg);
        }
        mtoast.setGravity(Gravity.CENTER,0,0);
        mtoast.show();
    }
}
