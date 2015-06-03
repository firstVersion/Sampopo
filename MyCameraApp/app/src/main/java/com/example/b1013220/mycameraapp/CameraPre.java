package com.example.b1013220.mycameraapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class CameraPre extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraView view = new CameraView(this);
        setContentView(view);
        //setContentView(R.layout.main);

    }


    protected void onResume(){
        super.onResume();
    }

    protected void onStop(){
        super.onStop();
    }

    public void onDestroy(){
        super.onDestroy();
    }
}


