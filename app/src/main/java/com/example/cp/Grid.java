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
    public boolean change(ArrayList<String> keys){
        boolean flag=false;
        for(int i=0;i<keys.size();i++){

            String k=keys.get(i);
            if(dc.topics.containsKey(k)){
                flag=true;
                dc.topics.put(k,(Integer.parseInt(dc.topics.get(k))+1)+"");
                dc.notifyDataSetChanged();
            }}
        return flag;
    }




}
