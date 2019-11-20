package com.example.firsttopic.MyTraffic;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class RoadTraffocAdapter extends PagerAdapter {
        private List list;
        RoadTraffocAdapter(List list ){
            this.list = list;
        }
    @Override
    public int getCount() {
        return list.size();//设置滚动页面的个数
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {//
        container.removeView((View) list.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView((View) list.get(position));
        return list.get(position);
    }
}
