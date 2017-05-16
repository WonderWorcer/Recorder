package com.recoder.recoder;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.Semaphore;

/**
 * Created by Роман on 05.05.2017.
 */

public class App extends Application {

    private static Context Context;
    private static String phoneNumber;
    private static Semaphore semaphore;
    private static CallRecord callRecord;
    public final static String PREF_PASSWORD = "prefPassword";
    public final static String PREF_PASSWORD_ACTIVE = "PrefPasswordActive";
    public final static String PREF_AUTOFILLBASE = "prefAutoFillBase";
    public final static String PREF_CHANGE_PASSWORD = "prefChangePassword";
    public final static String PREF_DELETE_AFTER_10_ATTEMPT = "prefDeleteAfter10Attempt";
    public final static String PREF_API_KEY = "PrefApiKey";
    public final static String PREF_ALL_FILES = "prefAllFiles";
    public final static String PREF_ANALIZED_FILES = "prefAnalizedFiles";
    public final static String PREF_ISRECORDING = "prefIsRecording";
    public static final String GOOGLE_RECOGNIZER_URL = "http://www.google.com/speech-api/v2/recognize?client=chromium&output=json";

    public static CallRecord getCallRecord() {
        return callRecord;
    }

    public static void setCallRecord(CallRecord mCallRecord) {
        callRecord = mCallRecord;
    }

    public static Context getContext() {
        return Context;
    }

    public static void setSemaphore(Semaphore mSemaphore) {
        semaphore = mSemaphore;
    }

    public static Semaphore getSemaphore() {
        return semaphore;
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