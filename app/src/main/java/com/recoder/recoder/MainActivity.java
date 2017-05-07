package com.recoder.recoder;

import android.content.res.Configuration;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;

import com.recoder.recoder.Helper.PrefsHelper;
import com.recoder.recoder.Semaphore.ThreadsApp;
import com.recoder.recoder.fragments.fragmentAnalyzeRecords;
import com.recoder.recoder.fragments.fragmentAnalyzeWords;
import com.recoder.recoder.fragments.fragmentRecords;
import com.recoder.recoder.fragments.fragmentSettings;
import com.recoder.recoder.fragments.fragmentWords;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawer;
    public NavigationView nvDrawer;
    public String PREF_API_KEY = "PrefApiKey";
    CallRecord callRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        callRecord = new CallRecord.Builder(this)
                .setRecordFileName("Record_" + new java.text.SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new java.util.Date()))
                .setRecordDirName("CallRecord")
                .setRecordDirPath(Environment.getExternalStorageDirectory().getPath())
                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                .setShowPhoneNumber(true)
                .setShowSeed(true)
                .build();
        callRecord.startCallRecordService();
        callRecord.startCallReceiver();
        PrefsHelper.writePrefString(this, PREF_API_KEY, "AIzaSyCvfglaj2kcmjzNY5kyItkBx5wsHXQm8Y4");
        App.setContext(this);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        selectDrawerItem(nvDrawer.getMenu().getItem(0));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ThreadsApp threadsApp = new ThreadsApp();
        threadsApp.threadController();

    }

    public void openDrawer() {
        mDrawer.openDrawer(Gravity.LEFT);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragmentRecords and specify the fragmentRecords to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_main_fragment:
                fragmentClass = fragmentRecords.class;
                break;
            case R.id.nav_setting_fragment:
                fragmentClass = fragmentSettings.class;
                break;
            case R.id.nav_library_fragment:
                fragmentClass = fragmentWords.class;
                break;
            case R.id.nav_analyze_fragment:
                fragmentClass = fragmentAnalyzeWords.class;
                break;
            case R.id.nav_analyze_zap_fragment:
                fragmentClass = fragmentAnalyzeRecords.class;
                break;
            default:
                fragmentClass = fragmentRecords.class;
        }


        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Insert the fragmentRecords by replacing any existing fragmentRecords
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);

        mDrawer.closeDrawers();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
