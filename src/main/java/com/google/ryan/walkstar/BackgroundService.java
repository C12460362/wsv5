package com.google.ryan.walkstar;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class BackgroundService extends IntentService{
    private static final String TAG = "com.google.walkstar";
    public BackgroundService(){
        super("BackgroundService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "The service has now started");
        MainActivity ins = new MainActivity();
       // ins.onStart();



    }
}
