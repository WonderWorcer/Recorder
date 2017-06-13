package com.recoder.recoder;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;

import com.recoder.recoder.Helper.FillBase;
import com.recoder.recoder.Helper.PrefsHelper;
import com.recoder.recoder.Semaphore.ThreadsApp;
import com.recoder.recoder.Tools.FileWorker;
import com.recoder.recoder.fragments.fragmentAnalyzeRecords;
import com.recoder.recoder.fragments.fragmentMainPage;
import com.recoder.recoder.fragments.fragmentRecords;
import com.recoder.recoder.fragments.fragmentSettings;
import com.recoder.recoder.fragments.fragmentWords;

import java.util.Locale;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Запускающий класс приложения, необходим для старта
 * рекордера, семафора.
 */

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawer;
    public NavigationView nvDrawer;
    CallRecord callRecord;///<Необходим для старта сервиса записи телефонных разговоров
    FileWorker fileWorker;///<Необходим для удаления записи, при истечении срока хранения
    @Override
    /**
     * Метод, стартующий приложение
     * Запускает запись звонков
     * Запускает поток семафора, для анализа записей
     * Удаляет записи по истечению срока хранения
     * Запускает окно пароля, если такая функция включена пользователем.
     */
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
        App.setCallRecord(callRecord);
        callRecord.startCallRecordService();
        if (PrefsHelper.readPrefBool(this, App.PREF_ISRECORDING)) {
            callRecord.startCallReceiver();
            PrefsHelper.writePrefBool(this, App.PREF_ISRECORDING, true);
        }
        PrefsHelper.writePrefBool(this, App.PREF_CHANGE_PASSWORD, false);
        PrefsHelper.writePrefString(this, App.PREF_API_KEY, "AIzaSyCvfglaj2kcmjzNY5kyItkBx5wsHXQm8Y4");
        App.setContext(this);
        fileWorker = new FileWorker();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        selectDrawerItem(nvDrawer.getMenu().getItem(0));


        if (PrefsHelper.readPrefBool(this, App.PREF_AUTOFILLBASE)) {
            FillBase fillBase = new FillBase();
            fillBase.fillAllBase();
            PrefsHelper.writePrefBool(this, App.PREF_AUTOFILLBASE, false);
        }

        if (PrefsHelper.readPrefInt(App.getContext(), App.PREF_AUTO_DELETE) > 0)
            fileWorker.deleteFilesAfterDateEnd(PrefsHelper.readPrefInt(App.getContext(), App.PREF_AUTO_DELETE));


        ThreadsApp threadsApp = new ThreadsApp();
        threadsApp.threadController();

        if (PrefsHelper.readPrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE)) {
            PrefsHelper.writePrefBool(App.getContext(), App.PREF_CHANGE_PASSWORD, true);
            Intent intent = new Intent(App.getContext(), PasswordActivity.class);
            startActivity(intent);
        }

    }

    /**
     * Включение меню, задание расположения меню слева
     */
    public void openDrawer() {
        mDrawer.openDrawer(Gravity.LEFT);
    }

    /**
     * @param navigationView
     */
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

    /**
     * Выбор пункта меню и последующее открытие выбранного фрагмента
     * @param menuItem - пункт меню
     */
    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_main_page:
                fragmentClass = fragmentMainPage.class;
                break;
            case R.id.nav_main_fragment:
                fragmentClass = fragmentRecords.class;
                break;
            case R.id.nav_setting_fragment:
                fragmentClass = fragmentSettings.class;
                break;
            case R.id.nav_library_fragment:
                fragmentClass = fragmentWords.class;
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

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();

        menuItem.setChecked(true);

        mDrawer.closeDrawers();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
