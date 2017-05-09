package com.recoder.recoder;

import android.app.Application;
import android.content.Context;

/**
 * Created by Роман on 05.05.2017.
 */

public class App extends Application {

    private static Context Context;
    private static String phoneNumber;
    public final static String PREF_PASSWORD = "prefPassword";
    public final static String PREF_PASSWORD_ACTIVE = "PrefPasswordActive";
    public final static String PREF_AUTOFILLBASE = "prefAutoFillBase";
    public final static String PREF_CHANGE_PASSWORD = "prefChangePassword";
    public final static String PREF_DELETE_AFTER_10_ATTEMPT = "prefDeleteAfter10Attempt";
    public final static String PREF_API_KEY = "PrefApiKey";
    public static final String GOOGLE_RECOGNIZER_URL = "http://www.google.com/speech-api/v2/recognize?client=chromium&output=json";
    public static Context getContext() {
        return Context;
    }


    public static void setContext(Context mContext) {
        Context = mContext;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        App.phoneNumber = phoneNumber;
    }
}