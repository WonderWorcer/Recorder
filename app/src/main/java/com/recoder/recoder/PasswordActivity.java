package com.recoder.recoder;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.Helper.PrefsHelper;
import com.recoder.recoder.Tools.FileWorker;
import com.recoder.recoder.view.PwdGestureView;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс - активити для работы с паролем
 */
public class PasswordActivity extends AppCompatActivity {
    PwdGestureView mPwdGestureView;///<Окно пароля
    String firstPassword = "";///<Первый введенный пароль
    boolean isFirstPasswordEnter = false;///<Флаг ввода первого пароля
    TextView tv_pwd, tv_input_pwd;
    RadioGroup rg_RadioGroup;
    DBHelper dbHelper = new DBHelper(App.getContext());///<Необходим для работы с базой данных
    SQLiteDatabase db = dbHelper.getWritableDatabase();///<Необходим для работы с базой данных
    int failedCount;///<Количество неверных попыток ввода пароля
    @Override
    /**
     * Инициализация активити
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_acivity);

        mPwdGestureView = (PwdGestureView) findViewById(R.id.pwd_view);

        tv_pwd = (TextView) findViewById(R.id.tv_pwd);
        tv_input_pwd = (TextView) findViewById(R.id.tv_input_pwd);
        rg_RadioGroup = (RadioGroup) findViewById(R.id.rg_RadioGroup);

        try {
            mPwdGestureView.setOldPwd(PrefsHelper.readPrefString(App.getContext(), App.PREF_PASSWORD));
            failedCount = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_pwd.setText("Введите пароль");
        mPwdGestureView.setIsDrawLine(true);
/**
 * Метод, работающий с вводом пароля
 * Смена пароля
 * Ввод пароля
 * Включение пароля
 * Удаление записей после 10 неверных попыток ввода пароля
 */
        mPwdGestureView.startWork(new PwdGestureView.GetPwd() {
            @Override
            public void onGetPwd(String pwd) {
                if (PrefsHelper.readPrefBool(App.getContext(), App.PREF_CHANGE_PASSWORD)) {
                    if (pwd == "true") {
                        PrefsHelper.writePrefBool(App.getContext(), App.PREF_CHANGE_PASSWORD, false);
                        PrefsHelper.writePrefString(App.getContext(), App.PREF_PASSWORD, null);
                        PrefsHelper.writePrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE, false);
                        mPwdGestureView.setOldPwd(null);
                        tv_pwd.setText("Введите пароль");
                    } else
                        tv_pwd.setText("Введите пароль верно");
                } else if (pwd == "true")
                    onBackPressed();
                else if (isFirstPasswordEnter) {
                    if (pwd.equals(firstPassword)) {
                        PrefsHelper.writePrefString(App.getContext(), App.PREF_PASSWORD, pwd);
                        PrefsHelper.writePrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE, true);
                        onBackPressed();
                    } else {
                        firstPassword = "";
                        isFirstPasswordEnter = false;
                        tv_pwd.setText("Пароль не верен, попробуйте еще раз");
                    }

                } else if (!PrefsHelper.readPrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE)) {
                    firstPassword = pwd;
                    isFirstPasswordEnter = true;
                    tv_pwd.setText("Повторите пароль");
                } else if (PrefsHelper.readPrefBool(App.getContext(), App.PREF_DELETE_AFTER_10_ATTEMPT)) {
                    failedCount++;
                    tv_pwd.setText(Integer.toString(failedCount) + "Неправильных попыток");
                    if (failedCount == 10) {
                        FileWorker fileWorker = new FileWorker();
                        fileWorker.deleteAllFiles();
                        db.delete(dbHelper.TABLE_RECORDS, null, null);
                        db.delete(dbHelper.TABLE_USED_WORDS, null, null);
                    }
                } else
                    tv_pwd.setText("Введите правильный пароль");
            }
        });


    }
}
