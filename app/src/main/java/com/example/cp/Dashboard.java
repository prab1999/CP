package com.example.cp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("unchecked")
public class Dashboard extends Fragment {
    TextView num,username;
    EditText code,subid;
    Button but;
    View view;
    String subUrl,quesurl;
    Context m;
    ArrayList<String> tags=new ArrayList<String>();
    boolean netconnected=false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);
        num = (TextView)view.findViewById(R.id.num);
        code = (EditText) view.findViewById(R.id.code);
        but=(Button)view.findViewById(R.id.button);
        username=(TextView)view.findViewById(R.id.username);
        subid=(EditText)view.findViewById(R.id.submission);
        HashMap<String,String> map=null;
        if(getArguments()!=null){
        map=(HashMap<String,String>)getArguments().getSerializable("hashmap");}


        if(map!=null){
        username.setText("HANDLE : "+map.get("id"));
        num.setText(map.get("num"));}
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

                siteUrl = "https://codeforces.com/contest/"+c.substring(0,ind)+"/submission/58780222";

                but.setEnabled(false);
                (new Checknet()).execute();
                (new ParseURL() ).execute();


            }});

        return view;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) m.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
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
                but.setEnabled(true);
            }
        }
    }


    private class Checknet extends AsyncTask<Void,Void,Void>{

        boolean initialstate;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            initialstate=haveNetworkConnection();
            netconnected=initialstate;
            but.setEnabled(false);
        }

        protected Void doInBackground(Void... voids) {
            while(!netconnected){
             netconnected=haveNetworkConnection();}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            but.setEnabled(true);
            if(initialstate==false){
            Toast toast=Toast.makeText(getActivity(),"Internet Connected",Toast.LENGTH_SHORT);
            toast.setMargin(150,150);
            toast.show();}
        }
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
                Document document = Jsoup.connect(siteUrl).get();
                System.out.println(document.empty());
                Elements em=document.select("h3");
                String wr=em.first().text();
                Elements elems=document.select("span.tag-box");
                for(Element tag:elems){
                    tags.add(tag.text());
                    System.out.println(tags.get(tags.size()-1));
                }
                if(tags.size()>0){
                tags.remove(tags.size()-1);}

            }
            catch(UnknownHostException e){
                System.out.println("wrong");
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




