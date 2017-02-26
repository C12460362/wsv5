package com.google.ryan.walkstar;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by ryan on 26/02/2017.
 */
public class CountSteps extends Service {
    private final IBinder mBinder = new LocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder
    {
        public CountSteps getService(){
            return CountSteps.this;
        }
    }
}
