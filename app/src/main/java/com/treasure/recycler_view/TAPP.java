package com.treasure.recycler_view;

import android.app.Application;
import android.content.Context;

/**
 * Created by treasure on 2018/8/30.
 */

public class TAPP extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
