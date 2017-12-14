package com.appsters.igit.taxicity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ScreenSlidePager extends FragmentStatePagerAdapter {
    public ScreenSlidePager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return IntroFrag.init(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
