package com.example.firsttopic.MultiMedia;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViolatFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mfraglist;

    public ViolatFragmentAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> list) {
        super(fm, behavior);
        mfraglist = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return   mfraglist.get(position);
    }

    @Override
    public int getCount() {
        return mfraglist.size();
    }
}
