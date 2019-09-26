package com.example.cp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;


public class TabAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> arr=new ArrayList<Fragment>();
    private HashMap<String,Boolean> map=new HashMap<>();
    public TabAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        arr.add(new Dashboard());
        arr.add(new Grid());
        arr.add(new Unsolved());
    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position){
        return arr.get(position);

    }
    public void addFragment(String tabname, String url, TabLayout tl, ViewPager vp){
       ProblemStatement pb=null;
       int ind=0;
       if(map.containsKey(tabname)){
       for(int k=3;k<getCount();k++){
           if(((ProblemStatement)arr.get(k)).name.equals(tabname)){
               ind=k;
               System.out.println("ghekl");
               break;
           }
       }

       }
       else{
           arr.add(new ProblemStatement(url,tabname));
           ind=getCount()-1;
           map.put(tabname,true);
           tl.addTab(tl.newTab().setText(tabname));
           this.notifyDataSetChanged();
       }


       vp.setCurrentItem(ind);
       vp.setOffscreenPageLimit(getCount()-1);



    }
    public void deleteFragment(String name,TabLayout tl,ViewPager vp){
        for(int p=3;p<this.getCount();p++){
            if(((ProblemStatement)arr.get(p)).name==name){
                arr.remove(arr.get(p));
                map.remove(name);
                this.notifyDataSetChanged();
                tl.removeTabAt(p);
                vp.setOffscreenPageLimit(getCount()-1);
                vp.setCurrentItem(2);
                break;

            }
        }


    }
}
