package com.example.dell.iotplatformdemo.Base;

import android.app.Application;

import com.lvqingyang.iot.net.EslabIot;

/**
 * Created by DELL on 2017/12/15.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //
         EslabIot.init("eslabtest");

    }
}
