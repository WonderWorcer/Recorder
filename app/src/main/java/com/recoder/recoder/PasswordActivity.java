package com.recoder.recoder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.recoder.recoder.Helper.PrefsHelper;
import com.recoder.recoder.view.PwdGestureView;

public class PasswordActivity extends AppCompatActivity {
    public final static String PREF_PASSWORD = "prefPassword";
    PwdGestureView mPwdGestureView;
    String firstPassword = "";
    boolean isFirstPasswordEnter = false;
    TextView tv_pwd, tv_input_pwd;
    RadioGroup rg_RadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_acivity);

        mPwdGestureView = (PwdGestureView) findViewById(R.id.pwd_view);

        tv_pwd = (TextView) findViewById(R.id.tv_pwd);
        tv_input_pwd = (TextView) findViewById(R.id.tv_input_pwd);
        rg_RadioGroup = (RadioGroup) findViewById(R.id.rg_RadioGroup);

        try {
            mPwdGestureView.setOldPwd(PrefsHelper.readPrefString(App.getContext(), PREF_PASSWORD));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mPwdGestureView.setOldPwd("012543");
        mPwdGestureView.setIsDrawLine(true);

        mPwdGestureView.startWork(new PwdGestureView.GetPwd() {
            @Override
            public void onGetPwd(String pwd) {
                if (pwd == "true")
                    onBackPressed();
                //tv_pwd.setText(pwd);
            }
        });


    }
}
