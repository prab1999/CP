package com.example.cp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


public class Unsolved extends Fragment {
    public ProblemAdapter pb;
    String[] args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_unsolved, container, false);
        pb=new ProblemAdapter(getActivity());
        GridView gv=(GridView)view.findViewById(R.id.unsolved);
        if(gv!=null)
        gv.setAdapter(pb);
        if(getArguments()!=null){
           args=getArguments().getStringArray("unsolved");
        }
        if(args!=null){
            System.out.println("hello");
            update(args);
        }
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent ik=new Intent(getActivity(),MainActivity.class);
                ik.putExtra("key","hello from fragment");
                startActivity(ik);

            }
        });



        return view;
    }

    public void update(String[] args){
        String[] codearr=args[0].split(" ");

        String[] attarr=args[1].split(" ");
        if(args[0].length()==0){
            codearr=new String[0];
        }

        pb.problems.clear();
        pb.attempts.clear();

        for(int i=0;i<codearr.length;i++){
                pb.problems.add(codearr[i]);
                pb.attempts.add(attarr[i]);

            }

        pb.notifyDataSetChanged();
    }


}
