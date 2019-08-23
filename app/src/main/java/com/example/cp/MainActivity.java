package com.example.cp;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {
    HashMap<String,String> user;
    Dashboard dash;
    Grid gd;
    Unsolved ud;
    String lastsubid;
    DB db;
    Net nt;
    ActionMenuItemView bt;
    Color c;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DB(this);
        nt=new Net(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Solved"));
        tabLayout.addTab(tabLayout.newTab().setText("Unsolved"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        TabAdapter tabsAdapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Intent i=getIntent();
        user=(HashMap<String, String>)i.getSerializableExtra("hashmap");
        dash=(Dashboard)tabsAdapter.getItem(0);
        gd=(Grid)tabsAdapter.getItem(1);
        ud=(Unsolved)tabsAdapter.getItem(2);
        changes();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            bt=findViewById(R.id.action_favorite);
            bt.setBackgroundColor(Color.parseColor("#FF1D8E9F"));
            if(nt.haveNetworkConnection()){
                Animation animation = new RotateAnimation(0.0f, 360.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                animation.setRepeatCount(-1);
                animation.setDuration(2000);
                bt.setAnimation(animation);

                (new Parse(user.get("id"))).execute();
            }
            else{
                Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_LONG).show();}
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private class Parse extends AsyncTask<Void,Void,Void> {
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
                retrieveOldData(userid,obj);

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            changes();
            Toast toast=Toast.makeText(MainActivity.this,"Refreshed",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();



        }
    }
    public void changes(){

        Bundle bd=new Bundle();
        bd.putSerializable("hashmap",user);
        dash.setArguments(bd);
        Bundle g=new Bundle();
        g.putString("tags",user.get("tags"));
        gd.setArguments(g);
        Bundle u=new Bundle();
        System.out.println("main "+user.get("unsolved")+"\n"+user.get("attempt"));
        u.putStringArray("unsolved",new String[]{user.get("unsolved"),user.get("attempt")});
        ud.setArguments(u);
        viewPager.getAdapter().notifyDataSetChanged();


    }

    public void retrieveOldData(String userid, JSONObject obj) throws JSONException {
        String questions=user.get("questions")+"";
        Tags t=new Tags();
        int num=Integer.parseInt(user.get("num"));
        int ntag=t.keys.size();
        int[] tagarr=Tags.convertarray(user.get("tags"));
        String code=user.get("unsolved"),att=user.get("attempt");
        System.out.print("ld data "+code+"  "+att);
        HashMap<String,Integer> unsolved = new HashMap<>();
        String[] codearr=code.split(" ");
        String[] attarr=att.split(" ");
        if(code.length()==0)codearr=new String[0];
        for(int i=0;i<codearr.length;i++){
            unsolved.put(codearr[i],Integer.parseInt(attarr[i]));
        }
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
            problem=temp.getJSONObject("problem");
            String cod=(problem.getString("contestId")+problem.getString("index"));
            if(temp.getString("verdict").equals("OK")){
                if(questions.lastIndexOf(cod)!=-1) continue;
                questions+=(cod+" ");
                if(code.lastIndexOf(cod)!=-1)unsolved.remove(cod);
                num++;

                tags=problem.getJSONArray("tags");
                for(int j=0;j<tags.length();j++){
                    tag=tags.getString(j);
                    tagarr[t.keys.get(tag)]++;
                }

            }
            else{
                if(questions.lastIndexOf(cod)==-1&&code. lastIndexOf(cod))){
                    Integer val=unsolved.get(cod);
                    if(val!=null){
                        unsolved.put(cod,val+=1);
                    }
                    else{
                        unsolved.put(cod,1);
                    }}
            }
        }
        code="";
        att="";
        for(String key:unsolved.keySet()){
            code+=(key+" ");
            att+=((unsolved.get(key))+" ");
        }
        db.UpdateUserDetails(questions,tagarr,userid,lastsub,lastver,lastcode,num,sec,att,code);
        user= db.GetUserByUserId(userid);


    }


}
