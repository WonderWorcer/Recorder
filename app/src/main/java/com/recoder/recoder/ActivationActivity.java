package com.recoder.recoder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.recoder.recoder.Helper.PrefsHelper;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс - активити для работы с активацией API - ключа
 */
public class ActivationActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView textView;
    @Override
    /**
     * Инициализация активити
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Активация");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textView = (TextView) findViewById(R.id.ApiKey);
        textView.setText(PrefsHelper.readPrefString(App.getContext(), App.PREF_API_KEY));

/**
 * Событие по кнопке
 * Смена API - ключа для приложения
 */
        findViewById(R.id.APIChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(ActivationActivity.this);
                View promptsView = li.inflate(R.layout.input_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ActivationActivity.this);
                alertDialogBuilder.setView(promptsView);
                final TextView dialogQuestion = (TextView) promptsView
                        .findViewById(R.id.dialog_question);
                final EditText input = (EditText) promptsView
                        .findViewById(R.id.dialog_input);
                dialogQuestion.setText("Введите API-ключ");
                input.setText("");

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        PrefsHelper.writePrefString(App.getContext(), App.PREF_API_KEY, input.getText().toString());
                                        Toast.makeText(App.getContext(), "Запись сохранена", Toast.LENGTH_SHORT).show();
                                        onRestart();

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();


            }
        });


    }

    @Override
    /**
     * Метод, необходимый для возврата в предыдущее меню
     */
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
