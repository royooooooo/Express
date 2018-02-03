package com.zhoufan.express.activity;

import android.app.Application;

import com.zhoufan.express.util.ToastUtil;



public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.getInstance().init(this);
    }
}
