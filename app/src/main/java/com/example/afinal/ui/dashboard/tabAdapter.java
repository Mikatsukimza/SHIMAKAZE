package com.example.afinal.ui.dashboard;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class tabAdapter extends FragmentPagerAdapter {
    private String[] list = {"今日","推荐","娱乐"};

    private Fragment[] mFragments = new Fragment[]{new MainFragment1(), new MainFragment2(), new MainFragment3()};


    public  tabAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }




    public  CharSequence getPageTitle(int position){
        return list[position];
    }
}
