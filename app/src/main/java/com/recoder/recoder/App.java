package com.recoder.recoder;

import android.app.Application;
import android.content.Context;

/**
 * Created by Роман on 05.05.2017.
 */

public class App extends Application {

    private static Context Context;

    public static Context getContext() {
        return Context;
    }

    public static void setContext(Context mContext) {
        Context = mContext;
    }


}