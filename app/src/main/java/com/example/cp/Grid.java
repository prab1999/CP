package com.example.cp;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.HashMap;

public class Grid extends Fragment {
    public DictionaryAdapter dc;
    String tags;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.grid, container, false);
        GridView gv = (GridView)view.findViewById(R.id.gridview);
        dc=new DictionaryAdapter(getActivity());
        gv.setAdapter(dc);
        if(getArguments()!=null){
            tags=getArguments().getString("tags");
        }


        if(tags!=null){
            updategrid(tags);
        }

        return view;
    }
    public boolean updategrid(String tag){
        String keys[]=tag.split("[|]");
        if(tag.length()==0)keys=new String[0];
        boolean flag=false;
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
