package com.example.cp;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class ProblemStatement extends Fragment {
    String url;
    WebView wv;
    Button bt;
    String name;
    public ProblemStatement(String url,String name) {
        super();
        this.url=url;
        this.name=name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_problem_statement, container, false);
        wv=(WebView)view.findViewById(R.id.web);
        bt=(Button)view.findViewById(R.id.btnDone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.deletetab(name);
            }
        });
        wv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_BACK&&keyEvent.getAction()== MotionEvent.ACTION_UP){
                if(wv.canGoBack()) {
                    wv.goBack();
                    return true;
                }

            }
                return false;
            }
        });
        startWebView(url);
        return view;
    }
    private void startWebView(String url) {

        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            public void onPageFinished(WebView view, String url) {
                try{

                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.loadUrl(url);


    }

}

