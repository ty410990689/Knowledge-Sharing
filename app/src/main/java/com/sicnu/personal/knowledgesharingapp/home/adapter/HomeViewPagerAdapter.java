package com.sicnu.personal.knowledgesharingapp.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private ArrayList<Fragment> fragmentArrayList;

    public HomeViewPagerAdapter(FragmentManager fm,Context context,ArrayList<Fragment> list) {
        super(fm);
        this.mContext = context;
        this.fragmentArrayList = list;
    }

    @Override
    public int getCount() {
        return fragmentArrayList == null? 0:fragmentArrayList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

}
