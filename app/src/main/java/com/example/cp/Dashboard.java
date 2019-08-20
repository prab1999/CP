package com.example.cp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
@SuppressWarnings("unchecked")
public class Dashboard extends Fragment {
    static TextView num,username,lastsub;
    Button but;
    View view;
    static String lastsubid;
    Context m;
    String[] tags;
    Net nt;
    ProgressBar pb;
    DB db;
    static HashMap<String,String> user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, container, false);
        num = (TextView)view.findViewById(R.id.num);
        lastsub=(TextView)view.findViewById(R.id.lastsub);
        but=(Button)view.findViewById(R.id.refresh);
        username=(TextView)view.findViewById(R.id.username);
        pb=(ProgressBar)view.findViewById(R.id.progressBar1);
        System.out.println(username.getText().toString()+"j");
        if(getArguments()!=null){
            user=(HashMap<String,String>)getArguments().getSerializable("hashmap");
            }


        if(user!=null){
            updatedashboard(user);
        }
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRefresh(username.getText().toString());
                but.setEnabled(false);
            }
        });


        nt=new Net(getActivity());
        db=new DB(getActivity());

        return view;
    }


    public void performRefresh(String id){
        pb.setIndeterminate(true);
        pb.setVisibility(View.VISIBLE);
        if(nt.haveNetworkConnection()){
            (new Parse(id)).execute();
        }
        else{
            Toast toast=Toast.makeText(getActivity(),"Internet Disconnected",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }

    }
    private class Parse extends AsyncTask<Void,Void,Void>{
        String userid;
        JSONObject obj;
        public Parse(String id){
            super();
            this.userid=id;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            obj=(new JsonParser(userid)).getJSONFromUrl();
            System.out.println(obj==null);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                retrieveOldData(userid,obj);

                } catch (JSONException e1) {
                e1.printStackTrace();
            }
            updatedashboard(user);
            Toast toast=Toast.makeText(getActivity(),"Refreshed",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
            but.setEnabled(true);
            pb.setVisibility(View.INVISIBLE);


        }
    }
    public void retrieveOldData(String userid,JSONObject obj) throws JSONException {
        String questions="";
        Tags t=new Tags();
        int num=Integer.parseInt(user.get("num"));
        int ntag=t.keys.size();
        int[] tagarr=convertarray(user.get("tags"));
        String lastsub="";
        String lastcode="";
        String lastver="";
        long sec=0;
        JSONArray res,tags;
        JSONObject temp,problem;
        String tag;
        res=obj.getJSONArray("result");
        if(res.length()>0){
            JSONObject p;
            p=res.getJSONObject(0);
            lastsub=p.getString("id");
            lastcode=p.getJSONObject("problem").getString("contestId")+p.getJSONObject("problem").getString("index");
            lastver=p.getString("verdict");
            sec=p.getLong("creationTimeSeconds");
        }
        for(int i=0;i<res.length();i++){
            temp=res.getJSONObject(i);
            if(temp.getString("id").equals(lastsubid)) {
                lastsubid=lastsub;
                break;
            }
            if(temp.getString("verdict").equals("OK")){
                num++;
                problem=temp.getJSONObject("problem");
                questions+=(problem.getString("contestId")+problem.getString("index")+" ");
                tags=problem.getJSONArray("tags");
                for(int j=0;j<tags.length();j++){
                    tag=tags.getString(j);
                    tagarr[t.keys.get(tag)]++;
                }

            }
        }
        db.UpdateUserDetails(questions,tagarr,userid,lastsub,lastver,lastcode,num,sec);
        user= db.GetUserByUserId(userid);


    }
    public void updatedashboard(HashMap<String,String> user) {

        username.setText(""+user.get("id"));
        num.setText("");
        num.setText(""+user.get("num"));
        setmulticolor(lastsub,user.get("code"),user.get("ver"),user.get("date"));
        lastsubid=user.get("sub");
        tags=(user.get("tags")).split("[|]");

        changes();
    }
    public void setmulticolor(TextView tv,String code,String ver,String date){


        String vercolor;
        if(ver.equals("OK")){
            vercolor="#00cc00";
        }
        else{
           vercolor="#e60000";
        }

        String text = "<b><font color=#cc2029>Last Submission<br></font></b> <font color=#ffcc00>" +
                code+"<br></font><font color="+vercolor+">"+ver+"</font><br>"+"<font color=#ffcc00>" +
        date+"<br></font>";
        tv.setText(Html.fromHtml(text));
    }
    public void changes(){
        Grid gd=(Grid)getFragmentManager().findFragmentById(R.id.fragment2);


        if(tags.length>0){
            boolean flag=true;
            if(gd!=null)
            flag=gd.change(tags);
            if(flag){
                Toast toast=Toast.makeText(getActivity(),"Added Successfully",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();

            }
        }
    }
    public int[] convertarray(String tagsi){
        String t[]=tagsi.split("[|]");
        int[] arr=new int[t.length];
        int i=0;
        for (String str : t)
            arr[i++] = Integer.parseInt(str);

        return arr;
    }

}




