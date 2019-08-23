package com.example.cp;

import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.*;
public class ProblemAdapter extends BaseAdapter {

    public ArrayList<String> problems=new ArrayList<>();
    public ArrayList<String> attempts=new ArrayList<>();

    private Context mcontext;
    int base_color=Color.parseColor("#330033");
    public ProblemAdapter(Context m){
        this.mcontext=m;
    }
    @Override
    public int getCount() {
        return attempts.size();
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
        String at=attempts.get(i);
        String prob=problems.get(i);


        TextView tv=new TextView(mcontext);

        tv.setLayoutParams(new GridView.LayoutParams(200,300));


        int color=Color.argb(Integer.parseInt(at)+10,Color.red(base_color),Color.green(base_color),Color.blue(base_color));
        tv.setBackgroundColor(color);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setPadding(8,8,8,8);
        tv.setGravity(Gravity.CENTER);
        String text="<font color='#000000'> "+prob+"</font><br><font color='#ff0280'>Attempts:  "+at+"</font>";
        tv.setText(Html.fromHtml(text));

        return tv;
    }
}

