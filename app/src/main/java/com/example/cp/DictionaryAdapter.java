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

    public HashMap<String,String> topics=new HashMap<>();
    public ArrayList<Integer> index=new ArrayList<>();
    public String[] keys={"2-sat","binary search","bitmasks","brute force","chinese remainder theorem",
            "combinatorics","constructive algorithms","data structures","dfs and similar",
            "divide and conquer","dp","dsu","expression parsing","fft","flows","games",
            "geometry","graph matchings","graphs","greedy","hashing","implementation",
            "interactive","math","matrices","meet-in-the-middle","number theory","probabilities",
            "schedules","shortest paths","sortings","string suffix structures","strings",
            "ternary search","trees","two pointers","*special"
    };
    private Context mcontext;
    int base_color=Color.parseColor("#330033");
    public DictionaryAdapter(Context m){
        this.mcontext=m;
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
        String value=(String)topics.get(keys[index.get(i)]);
        TextView tv=new TextView(mcontext);

        tv.setLayoutParams(new GridView.LayoutParams(200,300));


        int color=Color.argb(Integer.parseInt(value)+10,Color.red(base_color),Color.green(base_color),Color.blue(base_color));
        tv.setBackgroundColor(color);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setPadding(8,8,8,8);
        tv.setGravity(Gravity.CENTER);
        tv.setText(keys[index.get(i)]+":"+value);

        return tv;
    }
    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }



}
