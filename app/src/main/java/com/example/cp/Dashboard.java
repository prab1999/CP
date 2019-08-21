package com.example.cp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.Fragment;
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
        username=(TextView)view.findViewById(R.id.username);
        if(getArguments()!=null){
            user=(HashMap<String,String>)getArguments().getSerializable("hashmap");
            }


        if(user!=null){
            updatedashboard(user);
        }
        return view;
    }


    public void updatedashboard(HashMap<String,String> user) {
        username.setText(""+user.get("id"));
        num.setText("");
        num.setText(""+user.get("num"));
        setmulticolor(lastsub,user.get("code"),user.get("ver"),user.get("date"));
        lastsubid=user.get("sub");
        tags=(user.get("tags")).split("[|]");
    }

    public void setmulticolor(TextView tv, String code, String ver, String date){


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



}




