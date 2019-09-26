package com.example.cp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FirstScreen extends AppCompatActivity {
    private EditText ed;
    ProgressDialog pb;
    DB database=new DB(this);
    JSONObject obj;
    int status;
    HashMap<String, String> user;
    Net nt=new Net(this);
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        bt=(Button)findViewById(R.id.login);
        ed=(EditText)findViewById(R.id.id);
        pb=new ProgressDialog(this);
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setIndeterminate(false);
        pb.setTitle("Loading...");
        user=null;
        ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                    boolean flag=bt.performClick();
                }
                return false;
            }
        });
        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                pb.show();
                String userid=ed.getText().toString();
                boolean b=database.checkuser(userid);
                status=0;//0 for nothing,1 for success,-1 for failure
                if(!b){
                    if(nt.haveNetworkConnection()){
                    (new Parse(userid)).execute();
                    }
                    else{
                        Toast toast=Toast.makeText(FirstScreen.this,"Internet Disconnected",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                    }
                }
                else{
                user=database.GetUserByUserId(userid);
                pb.dismiss();
                Intent i=new Intent(FirstScreen.this,MainActivity.class);
                i.putExtra("hashmap",user);
                startActivity(i);}

        }});

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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {

                if(obj==null){

                    status=-1;
                }
                else{
                    status=1;
                    user=retrieveOldData(userid,obj);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            changes(userid);

        }
    }
    private void changes(String userid){
        pb.dismiss();
        if(status==-1){
            ed.selectAll();
            Toast toast=Toast.makeText(FirstScreen.this,"No Such Handle On codeforces",Toast.LENGTH_SHORT);
            toast.setMargin(250,150);
            toast.setGravity(Gravity.CENTER,0,-50);
            toast.show();}
        else {

            Intent i=new Intent(FirstScreen.this,MainActivity.class);
            i.putExtra("hashmap",user);
            startActivity(i);
        }
    }
    public HashMap<String,String> retrieveOldData(String userid,JSONObject obj) throws JSONException {
        String questions="";
        HashMap<String,Integer> unsolved = new HashMap<>();
        String code="",att="";
        Tags t=new Tags();
        int num=0;
        int ntag=t.keys.size();
        int[] tagarr=new int[ntag];
        String lastsub="";
        long sec=1211315671;
        String lastcode="";
        String lastver="";
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
            problem=temp.getJSONObject("problem");
            String cod=(problem.getString("contestId")+problem.getString("index"));
            if(temp.getString("verdict").equals("OK")){


                if(questions.lastIndexOf(cod)!=-1) continue;
                if(unsolved.containsKey(cod))unsolved.remove(cod);
                num++;
                questions+=(cod+" ");
                tags=problem.getJSONArray("tags");
                for(int j=0;j<tags.length();j++){
                    tag=tags.getString(j);
                    tagarr[t.keys.get(tag)]++;
                }

            }
            else{
                if(questions.lastIndexOf(cod)==-1){
                Integer val=unsolved.get(cod);
                if(val!=null){
                    unsolved.put(cod,val+=1);
                }
                else{
                    unsolved.put(cod,1);
                }}
            }
        }
        for(String key:unsolved.keySet()){
            code+=(key+" ");
            att+=((unsolved.get(key))+" ");
        }
        database.insertUserDetails(userid);
        database.UpdateUserDetails(questions,tagarr,userid,lastsub,lastver,lastcode,num,sec,att,code);
        return database.GetUserByUserId(userid);

    }

}
