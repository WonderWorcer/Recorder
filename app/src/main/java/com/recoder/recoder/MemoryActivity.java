package com.recoder.recoder;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.Helper.PrefsHelper;
import com.recoder.recoder.Tools.FileWorker;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс - активити для работы с памятью
 */
public class MemoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    DBHelper dbHelper = new DBHelper(App.getContext());///<Необходим для работы с базой данных
    SQLiteDatabase db = dbHelper.getWritableDatabase();///<Необходим для работы с базой данных

    @Override
    /**
     * Инициализация активити
     * Получение объема памяти, занимаемого записанными разговорами
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Память");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button button = (Button) findViewById(R.id.delete_after);
        if (PrefsHelper.readPrefInt(App.getContext(), App.PREF_AUTO_DELETE) > 0)
            button.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), App.PREF_AUTO_DELETE)));
        else
            button.setText("");
        Cursor cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID}, null, null, null, null, null);
        TextView countRecord = (TextView) findViewById(R.id.count_records);
        countRecord.setText(Integer.toString(cursor.getCount()));
        cursor.close();
        FileWorker fileWorker = new FileWorker();
        TextView countMemory = (TextView) findViewById(R.id.memry_used);
        Long memoryUsed = fileWorker.memoryUsed();
        if (memoryUsed > 1024 * 1024) {
            memoryUsed /= 1024 * 1024;
            countMemory.setText(Long.toString(memoryUsed) + " MB");
        }
        if (memoryUsed > 1024) {
            memoryUsed /= 1024;
            countMemory.setText(Long.toString(memoryUsed) + " KB");
        } else
            countMemory.setText(Long.toString(memoryUsed) + " B");
        findViewById(R.id.recordDirectory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(MemoryActivity.this);
                View promptsView = li.inflate(R.layout.input_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MemoryActivity.this);
                alertDialogBuilder.setView(promptsView);
                final TextView dialogQuestion = (TextView) promptsView
                        .findViewById(R.id.dialog_question);
                final EditText input = (EditText) promptsView
                        .findViewById(R.id.dialog_input);
                dialogQuestion.setText("Введите директорию для записи");
                input.setText(PrefsHelper.readPrefString(App.getContext(), App.PREF_DIR_PATH));

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        PrefsHelper.writePrefString(App.getContext(), App.PREF_DIR_PATH, input.getText().toString());

                                        Toast.makeText(MemoryActivity.this, "Директория изменена", Toast.LENGTH_SHORT).show();

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
/**
 * Событие по кнопке
 * Удаление всех файлов
 */
        findViewById(R.id.deleteNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileWorker fileWorker = new FileWorker();
                fileWorker.deleteAllFiles();
                db.delete(dbHelper.TABLE_USED_WORDS, null, null);
                db.delete(dbHelper.TABLE_RECORDS, null, null);
                Toast.makeText(MemoryActivity.this, "Записи успешно удалены", Toast.LENGTH_SHORT).show();
            }
        });

/**
 * Событие по кнопке
 * Удаление всех файлов после заданного количества дней
 */
        findViewById(R.id.delete_after).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MemoryActivity.this);
                View promptsView = li.inflate(R.layout.input_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MemoryActivity.this);
                alertDialogBuilder.setView(promptsView);
                final TextView dialogQuestion = (TextView) promptsView
                        .findViewById(R.id.dialog_question);
                final EditText input = (EditText) promptsView
                        .findViewById(R.id.dialog_input);
                dialogQuestion.setText("Введите число");
                input.setText("");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            PrefsHelper.writePrefInt(App.getContext(), App.PREF_AUTO_DELETE, Integer.parseInt(input.getText().toString()));
                                            Toast.makeText(MemoryActivity.this, "Изменения будут применены после перезапуска программы", Toast.LENGTH_SHORT).show();
                                        } catch (NumberFormatException ex) {
                                            Toast.makeText(MemoryActivity.this, "Необходимо ввести число", Toast.LENGTH_SHORT).show();
                                        }
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
