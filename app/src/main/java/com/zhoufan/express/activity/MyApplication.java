package com.zhoufan.express.activity;

import android.app.Application;

import com.zhoufan.express.util.ToastUtil;

/**
 * Created by fuyi on 2018/1/31.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.getInstance().init(this);
    }
}
