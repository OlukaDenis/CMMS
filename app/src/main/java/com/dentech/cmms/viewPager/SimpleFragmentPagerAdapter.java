package com.dentech.cmms.viewPager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] pageTitles;
    public static final int tabCount = 2;

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new ProfileFragment();
        }
        else if(position==1){
            return new NotificationFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public void setPageTitles(String[] pageTitles){
        this.pageTitles = pageTitles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if(position < tabCount){
            return pageTitles[position];
        }
       return null;
    }
}
