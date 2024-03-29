package com.example.firsttopic;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firsttopic.EighthTops.EighthTopActivity;
import com.example.firsttopic.Feedback.FeedbackActivity;
import com.example.firsttopic.MultiMedia.ViolationrecordActivity;
import com.example.firsttopic.LightingManagement.LightManagActivity;
import com.example.firsttopic.MyTraffic.MyTrafficActivity;
import com.example.firsttopic.Mycar.MyCarActivity;
import com.example.firsttopic.RoadSituation.Road_SituationActivity;
import com.example.firsttopic.Tollinquiry.TollInquiryActivity;
import com.example.firsttopic.Violationenquiry.ViolationEnquiryActivity;
import com.example.firsttopic.ETC.firsttopActivity;
import com.example.firsttopic.Environmental.IndexActivity;
import com.example.firsttopic.highstatus.HighsTatusActivity;
import com.example.firsttopic.leftIndex.LeftIndexActivity;
import com.example.firsttopic.newsmedia.NewsMediaActivity;
import com.example.firsttopic.ninthtop.NinthtopActivity;
import com.example.firsttopic.seventhtop.SeventhTopActivity;
import com.example.firsttopic.sixgettop.ChatActivity;
import com.example.firsttopic.BillManag.TherrtopActivity;
import com.example.firsttopic.transportquery.TransportQueryActivity;
import com.example.firsttopic.RedGreenReed.twotopActivity;

import java.util.ArrayList;
import java.util.List;

//import com.ziker.train.ETCActivity;
//import com.ziker.train.ManagerActivity;
//import com.ziker.train.RedGreenActivity;

public class MyAppCompatActivity extends AppCompatActivity {
    public String TAG = "MyAppCompatActivity";
    private List<Integer> idList = new ArrayList<>();
    private List<Class<?>> ActivityList = new ArrayList<>();
    private static int i = 0;
    private Menu menu;

    @Override
    protected void onRestart() {
        super.onRestart();
        i--;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyAppCompatActivity", "onCreate: "+getClass().toString());
        i++;
        idList.add(R.id.T_Mainactivity);
        idList.add(R.id.T_ETCactivity);
        idList.add(R.id.T_RedGreedactivity);
        idList.add(R.id.T_Manageractivity);
        idList.add(R.id.T_Violationactivity);
        idList.add(R.id.T_Texting);
        idList.add(R.id.T_longing);
        idList.add(R.id.T_fazhi);
        idList.add(R.id.T_gobaidey);
        idList.add(R.id.T_account);
        idList.add(R.id.T_Trip);
        idList.add(R.id.T_lempManag);
        idList.add(R.id.T_violationgo);
        idList.add(R.id.T_Roadcondition);
        idList.add(R.id.T_leftindex);
        idList.add(R.id.T_myopinion);
        idList.add(R.id.T_highsTat);
        idList.add(R.id.T_journalism);
        idList.add(R.id.T_TollInquiry);
        idList.add(R.id.T_MyCar);
        idList.add(R.id.T_myTraffoc);
        ActivityList.add(MainActivity.class);
        ActivityList.add(firsttopActivity.class);
        ActivityList.add(twotopActivity.class);
        ActivityList.add(TherrtopActivity.class);
        ActivityList.add(ViolationrecordActivity.class);
        ActivityList.add(IndexActivity.class);
        ActivityList.add(ChatActivity.class);
        ActivityList.add(SeventhTopActivity.class);
        ActivityList.add(EighthTopActivity.class);
        ActivityList.add(NinthtopActivity.class);
        ActivityList.add(TransportQueryActivity.class);
        ActivityList.add(LightManagActivity.class);
        ActivityList.add(ViolationEnquiryActivity.class);
        ActivityList.add(Road_SituationActivity.class);
        ActivityList.add(LeftIndexActivity.class);
        ActivityList.add(FeedbackActivity .class);
        ActivityList.add(HighsTatusActivity.class);
        ActivityList.add(NewsMediaActivity.class);
        ActivityList.add(TollInquiryActivity.class);
        ActivityList.add(MyCarActivity.class);
        ActivityList.add(MyTrafficActivity.class);
//        ActivityList.add(RedGreenActivity.class);
//        ActivityList.add(ManagerActivity.class);
    }
    public Menu setMenu(Context context, String Title, Integer Top_more_id){
        View Top_View = findViewById(android.R.id.content);
        ViewGroup parent = (ViewGroup) Top_View.getParent();
        parent.removeView(Top_View);
        menu = new Menu(context,Top_View,Title, R.drawable.list,Top_more_id,R.layout.linear_menu);
        menu.getLinear_main_menu().setBackground(getResources().getDrawable(R.drawable.long_button_gradient,null));
        AnimationDrawable animationDrawablemenu = (AnimationDrawable) menu.getLinear_main_menu().getBackground();
        animationDrawablemenu.setExitFadeDuration(1000);
        animationDrawablemenu.start();
        View left = menu.getLinear_left();
        left.setBackground(getResources().getDrawable(R.drawable.long_button_gradient,null));
        AnimationDrawable animationDrawableleft = (AnimationDrawable) left.getBackground();
        animationDrawableleft.setExitFadeDuration(1000);
        animationDrawableleft.start();
        try {
            if(idList.size()!=0){
                if(i>1)
                    menu.setOnMenuItemStartActivity(idList, ActivityList, v -> finish());
                else
                    menu.setOnMenuItemStartActivity(idList,ActivityList,null);
            }else{
                Log.d("MyActivityCompat", "setMenu: "+ActivityList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parent.addView(menu);
        try {
            return menu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
