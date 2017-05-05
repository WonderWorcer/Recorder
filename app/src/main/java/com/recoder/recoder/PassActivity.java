package com.recoder.recoder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class PassActivity extends AppCompatActivity {

    Toolbar toolbar;
    CheckBox onOffPassword;
    CheckBox deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Пароль");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        onOffPassword = (CheckBox) findViewById(R.id.onOffPassword);
        deleteAll = (CheckBox) findViewById(R.id.deleteAll);

        onOffPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(onOffPassword.isChecked()){
                    Toast.makeText(PassActivity.this, "Включил пароль", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PassActivity.this, "Выключил пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(deleteAll.isChecked()){
                    Toast.makeText(PassActivity.this, "Включил удаление", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PassActivity.this, "Выключил удаление", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.llChangePass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PassActivity.this, "Сменить пароль", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
