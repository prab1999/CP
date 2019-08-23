package com.example.cp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class TabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private ArrayList<Fragment> arr=new ArrayList<Fragment>();

     ;
    public TabAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        arr.add(new Dashboard());
        arr.add(new Grid());
        arr.add(new Unsolved());
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position){
        return arr.get(position);

    }
    public void addFragment(Fragment f){
        arr.add(f);
        mNumOfTabs++;
    }
}
