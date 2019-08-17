package com.example.cp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Grid extends Fragment {
    public DictionaryAdapter dc;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.grid, container, false);
        GridView gv = (GridView)view.findViewById(R.id.gridview);
        dc=new DictionaryAdapter(getActivity());
        gv.setAdapter(dc);
        return view;
    }
    public boolean change(String[] keys){
        boolean flag=false;
        System.out.println(dc.keys.length);
        for(int i=0;i<keys.length;i++){

            String k=dc.keys[i];
                flag=true;
                if(!keys[i].equals("0")){
                dc.index.add(i);
                dc.topics.put(k,(keys[i]+""));}

            }
        dc.notifyDataSetChanged();
        return flag;
    }




}
