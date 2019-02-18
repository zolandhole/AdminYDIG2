package com.example.adminydig2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.adminydig2.fragment.TabFragment1;
import com.example.adminydig2.fragment.TabFragment2;
import com.example.adminydig2.fragment.TabFragment3;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mJumlahTab;
    public PagerAdapter(FragmentManager fm, int jumlahTab) {
        super(fm);
        this.mJumlahTab = jumlahTab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new TabFragment1();
            case 1:
                return new TabFragment2();
            case 2:
                return new TabFragment3();
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return mJumlahTab;
    }
}
