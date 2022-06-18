package com.fxy.greatassignment.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class WriteFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment>fragmentList;
    String[]title = {"支出","收入"};//设置标题  之后用于横向展示传入
    public WriteFragmentPagerAdapter(@NonNull FragmentManager fragmentManager,List<Fragment>fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}

