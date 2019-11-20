package com.example.firsttopic.sixgettop;


import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.viewpager.widget.PagerAdapter;




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
