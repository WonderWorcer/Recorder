package com.recoder.recoder.Helper;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by WonderWorcer on 8.12.2016.
 */

public class PrefsHelper {

    private static final String DEFAULT_PREF_NAME = PrefsHelper.class.getSimpleName();
    private static final String DEFAULT_STRING_VALUE = null;
    private static final int DEFAULT_INT_VALUE = 0;
    private static final boolean DEFAULT_BOOLEAN_VALUE = false;
    ////
    private Context mContext;
    private SharedPreferences mPref;

    /**
     * @param context
     * @param pref_name
     */
    public PrefsHelper(Context context, String pref_name) {
        mContext = context;
        mPref = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
    }

    /**
     * @param context
     */
    public PrefsHelper(Context context) {
        mContext = context;
        mPref = getDefaultPreference(context);
    }

    /**
     * @param context
     * @return
     */
    public static SharedPreferences getDefaultPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * @param context
     * @param key
     * @param value
     */
    public static void writePrefString(Context context, String key, String value) {
        new PrefsHelper(context).getPrefEditor().putString(key, value).commit();
    }

    /**
     * @param context
     * @param key
     * @return
     */
    public static String readPrefString(Context context, String key) {
        return new PrefsHelper(context).getPreference().getString(key, DEFAULT_STRING_VALUE);
    }

    /**
     * @param context
     * @param key
     * @param value
     */
    public static void writePrefInt(Context context, String key, int value) {
        new PrefsHelper(context).getPrefEditor().putInt(key, value).commit();
    }

    /**
     * @param context
     * @param key
     * @return
     */
    public static int readPrefInt(Context context, String key) {
        return new PrefsHelper(context).getPreference().getInt(key, DEFAULT_INT_VALUE);
    }
    //

    /**
     * @param context
     * @param key
     * @param value
     */
    public static void writePrefBool(Context context, String key, boolean value) {
        new PrefsHelper(context).getPrefEditor().putBoolean(key, value).commit();
    }

    /**
     * @param context
     * @param key
     * @return
     */
    public static boolean readPrefBool(Context context, String key) {
        return new PrefsHelper(context).getPreference().getBoolean(key, DEFAULT_BOOLEAN_VALUE);
    }
    //

    /**
     * @param context
     */
    public static void clearPreference(Context context) {
        new PrefsHelper(context).getPreference().edit().clear().apply();

    }

    /**
     * @return
     */
    public SharedPreferences getPreference() {
        return mPref;
    }
    //

    /**
     * @return
     */
    public SharedPreferences.Editor getPrefEditor() {
        return getPreference().edit();
    }




    /**
     * @param <E>
     */
    public class EnumPreference<E extends Enum<E>> {
        private final SharedPreferences preferences;
        private final Class<E> clazz;
        private final String key;
        private final E defaultValue;

        /**
         * @param preferences
         * @param clazz
         * @param key
         * @param defaultValue
         */
        public EnumPreference(@NonNull SharedPreferences preferences,
                              @NonNull Class<E> clazz,
                              @NonNull String key,
                              @NonNull E defaultValue) {
            this.preferences = preferences;
            this.clazz = clazz;
            this.key = key;
            this.defaultValue = defaultValue;
        }
/*
*
 */

        @NonNull
        public E get() {
            final String string = preferences.getString(key, null);
            return string != null ? E.valueOf(clazz, string) : defaultValue;
        }

        /**
         * @return
         */
        @SuppressWarnings("UnusedDeclaration")
        public boolean isSet() {
            return preferences.contains(key);
        }

        /**
         * @param value
         */
        public void set(@Nullable E value) {
            preferences.edit().putString(key, value != null ? value.name() : null).apply();
        }

        /**
         *
         */
        @SuppressWarnings("UnusedDeclaration")
        public void delete() {
            preferences.edit().remove(key).apply();
        }
    }

    /**
     *
     */
    public class StringPreference {
        private final SharedPreferences preferences;
        private final String key;
        private final String defaultValue;

        /**
         * @param preferences
         * @param key
         */
        public StringPreference(@NonNull SharedPreferences preferences, @NonNull String key) {
            this(preferences, key, null);
        }

        /**
         * @param preferences
         * @param key
         * @param defaultValue
         */
        public StringPreference(@NonNull SharedPreferences preferences, @NonNull String key, @Nullable String defaultValue) {
            this.preferences = preferences;
            this.key = key;
            this.defaultValue = defaultValue;
        }

        /**
         * @return
         */
        @Nullable
        public String get() {
            return preferences.getString(key, defaultValue);
        }

        /**
         * @return
         */
        public boolean isSet() {
            return preferences.contains(key);
        }

        /**
         * @param value
         */
        public void set(@Nullable String value) {
            preferences.edit().putString(key, value).apply();
        }

        /**
         *
         */
        public void delete() {
            preferences.edit().remove(key).apply();
        }
    }

    /**
     *
     */
    public class IntPreference {
        private final SharedPreferences preferences;
        private final String key;
        private final int defaultValue;

        /**
         * @param preferences
         * @param key
         */
        public IntPreference(@NonNull SharedPreferences preferences, @NonNull String key) {
            this(preferences, key, 0);
        }

        /**
         * @param preferences
         * @param key
         * @param defaultValue
         */
        public IntPreference(@NonNull SharedPreferences preferences, @NonNull String key, int defaultValue) {
            this.preferences = preferences;
            this.key = key;
            this.defaultValue = defaultValue;
        }

        /**
         * @return
         */
        public int get() {
            return preferences.getInt(key, defaultValue);
        }

        /**
         * @return
         */
        public boolean isSet() {
            return preferences.contains(key);
        }

        /**
         * @param value
         */
        public void set(int value) {
            preferences.edit().putInt(key, value).apply();
        }

        /**
         *
         */
        public void delete() {
            preferences.edit().remove(key).apply();
        }
    }

    /**
     *
     */
    public class BooleanPreference {
        private final SharedPreferences preferences;
        private final String key;
        private final boolean defaultValue;

        /**
         * @param preferences
         * @param key
         */
        public BooleanPreference(@NonNull SharedPreferences preferences, @NonNull String key) {
            this(preferences, key, false);
        }

        /**
         * @param preferences
         * @param key
         * @param defaultValue
         */
        public BooleanPreference(@NonNull SharedPreferences preferences, @NonNull String key, boolean defaultValue) {
            this.preferences = preferences;
            this.key = key;
            this.defaultValue = defaultValue;
        }

        /**
         * @return
         */
        public boolean get() {
            return preferences.getBoolean(key, defaultValue);
        }

        /**
         * @return
         */
        public boolean isSet() {
            return preferences.contains(key);
        }

        /**
         * @param value
         */
        public void set(boolean value) {
            preferences.edit().putBoolean(key, value).apply();
        }

        /**
         *
         */
        public void delete() {
            preferences.edit().remove(key).apply();
        }
    }

}
