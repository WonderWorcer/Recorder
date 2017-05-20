package com.recoder.recoder.Tools;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;

import java.io.File;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 17 мая 2017
 * <p>
 * Класс, реализующий работу с файлами - удаление файлов, как всех, что находятся в базе данных, так и определенных, определение размера всех файлов в базе данных
 */


public class FileWorker {
    private static final String TAG = FileWorker.class.getSimpleName();
    DBHelper dbHelper = new DBHelper(App.getContext());
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    /**
     * Метод, который удаляет все файлы,
     * находящиеся в базе данных
     */
    public void deleteAllFiles() {
        File file;
        Cursor cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.RECORD_PATH},
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int pathColIndex = cursor.getColumnIndex(dbHelper.RECORD_PATH);
            file = new File(cursor.getString(pathColIndex));
            file.delete();
        }
        cursor.close();
    }

    /**
     * Метод, который удаляет файл по заданному пути
     *
     * @param path путь к файлу
     */
    public void deleteSelectedFile(String path) {
        File file = new File(path);
        file.delete();
    }

    /**
     * Метод, удаляющий файлы, после N дней
     * @param daysBack - число дней, после которого происходит удаление
     */
    public void deleteFilesAfterDateEnd(int daysBack) {

        File file;
        long purgeTime = System.currentTimeMillis() - (daysBack * 24 * 60 * 60 * 1000);

        Cursor cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.RECORD_PATH},
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int pathColIndex = cursor.getColumnIndex(dbHelper.RECORD_PATH);
            do {
                file = new File(cursor.getString(pathColIndex));
                if (file.lastModified() < purgeTime) {
                    if (!file.delete()) {
                        Log.i(TAG, "Файл не удален");
                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * Метод, который возвращает размер всех файлов из базы данных
     *
     * @return размер всех файлов в байтах
     */
    public long memoryUsed() {
        long usedMemory = 0;
        File file;
        Cursor cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.RECORD_PATH},
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int pathColIndex = cursor.getColumnIndex(dbHelper.RECORD_PATH);
            do {
                file = new File(cursor.getString(pathColIndex));
                usedMemory += file.length();
            } while (cursor.moveToNext());
        }
        cursor.close();
        return usedMemory;
    }
}
