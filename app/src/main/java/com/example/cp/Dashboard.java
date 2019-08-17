package com.example.cp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unchecked")
public class Dashboard extends Fragment {
    TextView num,username,code,ver;
    Button but;
    View view;
    String siteurl;
    Context m;
    String[] tags;
    boolean netconnected=false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);
        num = (TextView)view.findViewById(R.id.num);
        code=(TextView)view.findViewById(R.id.lastcode);
        ver=(TextView)view.findViewById(R.id.lastver);

        but=(Button)view.findViewById(R.id.refresh);
        username=(TextView)view.findViewById(R.id.username);
        HashMap<String,String> map=null;
        if(getArguments()!=null){
        map=(HashMap<String,String>)getArguments().getSerializable("hashmap");}


        if(map!=null){
        username.setText("HANDLE : "+map.get("id"));
        num.setText(map.get("num"));
        code.setText(map.get("code"));
        ver.setText(map.get("ver"));
        tags=(map.get("tags")).split(" ");
        changes();

        }
        return view;
    }


    public void changes(){
        Grid gd=(Grid)getFragmentManager().findFragmentById(R.id.fragment2);
        if(tags.length>0){
            boolean flag=gd.change(tags);
            if(flag){
                Toast toast=Toast.makeText(getActivity(),"Added Successfully",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                but.setEnabled(true);
            }
        }
    }

}




