package com.example.cp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {
    HashMap<String,String> a;
    boolean  b=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent i=getIntent();
        a=(HashMap<String, String>) i.getSerializableExtra("hashmap");



        FragmentManager fragmentManager = getFragmentManager();
        Dashboard samplefragment = new Dashboard();
        Bundle bd=new Bundle();
        bd.putSerializable("hashmap",a);
        samplefragment.setArguments(bd);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, samplefragment);
        fragmentTransaction.commit();
        System.out.println(bd.size());
        ;

    }
    public HashMap<String,String> getMap(){
        System.out.println(b);return a;
    }
    private class Mymap extends HashMap<String,String>{}

}
