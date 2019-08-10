package com.example.cp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class Dashboard extends Fragment {
    TextView num;
    EditText code;
    Button but;
    View view;
    String siteUrl;
    Context m;
    ArrayList<String> tags=new ArrayList<String>();
    public void changes(){
        Grid gd=(Grid)getFragmentManager().findFragmentById(R.id.fragment2);
        if(!tags.isEmpty()){
            boolean flag=gd.change(tags);
            if(flag){
                num.setText((Integer.parseInt(num.getText().toString())+1)+"");
                Toast toast=Toast.makeText(getActivity(),"Added Successfully",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                tags.clear();
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);
        num = (TextView)view.findViewById(R.id.num);
        code = (EditText) view.findViewById(R.id.code);
        but=(Button)view.findViewById(R.id.button);
        m=getActivity();
        but.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {   String c=code.getText().toString();
                int ind=c.length()-1;
                for(int i=0;i<c.length();i++){
                    if((int)c.charAt(i)>=65&&(int)c.charAt(i)<91){
                        ind=i;
                        break;
                    }
                }

                siteUrl = "https://codeforces.com/problemset/problem/"+c.substring(0,ind)+"/"+c.substring(ind);
                (new ParseURL() ).execute();


        }});

        return view;
    }
    private class ParseURL extends AsyncTask<Void, Void, Void> {
        ProgressBar progressBar=(ProgressBar)getActivity().findViewById(R.id.progressBar1);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                // Connect to the web site
                Document document = Jsoup.connect(siteUrl).get();
                Elements elems=document.select("span.tag-box");
                for(Element tag:elems){
                    tags.add(tag.text());
                    System.out.println(tags.get(tags.size()-1));
                }
                tags.remove(tags.size()-1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set downloaded image into ImageVie
            changes();
            progressBar.setVisibility(View.GONE);
        }

    }

}




