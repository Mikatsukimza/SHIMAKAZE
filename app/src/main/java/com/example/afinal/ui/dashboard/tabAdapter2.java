package com.example.afinal.ui.dashboard;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.afinal.ui.dashboard.Fragment3Tab.Fragment3_QR;
import com.example.afinal.ui.dashboard.Fragment3Tab.Fragment3_tips;

public class tabAdapter2 extends FragmentPagerAdapter {
    private String[] list = {"QR","天气提醒"};

    private Fragment[] mFragments = new Fragment[]{new Fragment3_QR(), new Fragment3_tips()};


    public  tabAdapter2(FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
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
