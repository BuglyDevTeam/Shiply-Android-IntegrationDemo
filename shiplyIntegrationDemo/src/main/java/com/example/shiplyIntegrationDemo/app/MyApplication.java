package com.example.shiplyIntegrationDemo.app;


import android.app.Application;
import com.example.shiplyIntegrationDemo.InitUtil;


public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        InitUtil.initShiplySDK(this, null);
    }


}
