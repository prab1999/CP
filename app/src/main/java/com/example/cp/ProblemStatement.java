package com.example.cp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ProblemStatement extends Fragment {
    String url;
    public ProblemStatement(String url) {
        super();
        this.url=url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_problem_statement, container, false);

        return view;
    }

}
