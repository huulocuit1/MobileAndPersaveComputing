package com.example.hl_th.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;

import utils.MyLocation;

/**
 * Created by HL_TH on 12/4/2016.
 */

public class MyEventListener extends AppCompatActivity {

    private GoogleMap googleMap;
    private Activity activity;
    private Button btn_search;
    private Button btn_my_location;

    private MyLocation myLocation;

    public MyEventListener(Activity activity, GoogleMap googleMap, MyLocation myLocation) {
        this.activity = activity;
        this.googleMap = googleMap;
        this.myLocation = myLocation;
        initViewMainActivity();
        myEvent();


    }

    public void initViewMainActivity() {
        this.btn_my_location = (Button) activity.findViewById(R.id.id_btn_location);
    }

    public void myEvent() {
        this.btn_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    myLocation.showMyLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }


}
