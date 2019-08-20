package com.example.cp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {
    HashMap<String,String> a;
    Dashboard samplefragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent i=getIntent();
        a=(HashMap<String, String>) i.getSerializableExtra("hashmap");



        FragmentManager fragmentManager = getFragmentManager();
        samplefragment = new Dashboard();
        Grid gd=new Grid();
        Bundle bd=new Bundle();
        bd.putSerializable("hashmap",a);
        samplefragment.setArguments(bd);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, samplefragment,"dash");
        fragmentTransaction.commit();
        ;

    }


    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
