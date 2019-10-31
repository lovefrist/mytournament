package com.example.firsttopic.sixgettop;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.firsttopic.R;

import java.util.ArrayList;

public class MyChartadapt extends PagerAdapter {
    private ArrayList<View> mlayoutlist;
    MyChartadapt(ArrayList<View> layoutlist) {
        mlayoutlist = layoutlist;
    }

    @Override
    public int getCount() {

        return mlayoutlist.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mlayoutlist.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       container.addView(mlayoutlist.get(position));
        return mlayoutlist.get(position);
    }
}
