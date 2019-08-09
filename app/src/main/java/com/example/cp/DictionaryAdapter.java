package com.example.cp;

import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.*;
public class DictionaryAdapter extends BaseAdapter {

    public HashMap<String,String> topics=new HashMap();
    public String[] keys={"2-sat","binary search","bitmasks","brute force","chinese remainder theorem",
            "combinatorics","constructive algorithms","data structures","dfs and similar",
            "divide and conquer","dp","dsu","expression parsing","fft","flows","games",
            "geometry","graph matchings","graphs","greedy","hashing","implementation",
            "interactive","math","matrices","meet-in-the-middle","number theory","probabilities",
            "schedules","shortest paths","sortings","string suffix structures","strings",
            "ternary search","trees","two pointers"
    };
    private Context mcontext;
    public DictionaryAdapter(Context m){
        this.mcontext=m;

        for(int i=0;i<keys.length;i++){
            topics.put(keys[i],"0");
        }
    }
    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv=new TextView(mcontext);
        tv.setLayoutParams(new GridView.LayoutParams(200,300));
        String value=(String)topics.get(keys[i]);
        int level=150-Integer.parseInt(value);
        if(level<=0){level=50;}
        tv.setBackgroundColor(Color.rgb(2*level,level,level));
        tv.setTextColor(Color.rgb(255,255,255));
        tv.setPadding(8,8,8,8);
        tv.setGravity(Gravity.CENTER);
        tv.setText(keys[i]+":"+value);

        return tv;
    }



}
