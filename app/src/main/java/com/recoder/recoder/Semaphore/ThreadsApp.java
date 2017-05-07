package com.recoder.recoder.Semaphore;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;

import java.util.concurrent.Semaphore;

/**
 * Created by Роман on 07.05.2017.
 */

public class ThreadsApp {
    Context context = App.getContext();
    DBHelper dbHelper = new DBHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    public void threadController() {
        Cursor cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.RECORD_PATH, dbHelper.KEY_ID}
                , dbHelper.RECORD_STATUS + "= ?", new String[]{"NotChecked"}, null, null, null);
        // ставим позицию курсора на первую строку выборки
        Semaphore sem = new Semaphore(1);

        if (cursor.moveToFirst()) {

            int recordPathColIndex = cursor.getColumnIndex(dbHelper.RECORD_PATH);
            int idColIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
            do {
                new Responser(sem, cursor.getString(idColIndex), cursor.getString(recordPathColIndex)).run();
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (cursor.moveToNext());
        }

        cursor.close();


    }
}
