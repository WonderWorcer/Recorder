package com.recoder.recoder.Tools;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;

import java.io.File;

/**
 * Created by Роман on 17.05.2017.
 */

public class FileWorker {
    private static final String TAG = FileWorker.class.getSimpleName();
    DBHelper dbHelper = new DBHelper(App.getContext());
    SQLiteDatabase db = dbHelper.getWritableDatabase();

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

    public void deleteSelectedFile(String path) {
        File file = new File(path);
        file.delete();
    }

    public void deleteFilesAfterDateEnd(int daysBack) {

        File file;
        long purgeTime = System.currentTimeMillis() - (daysBack * 24 * 60 * 60 * 1000);

        Cursor cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.RECORD_PATH},
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int pathColIndex = cursor.getColumnIndex(dbHelper.RECORD_PATH);
            file = new File(cursor.getString(pathColIndex));
            if (file.lastModified() < purgeTime) {
                if (!file.delete()) {
                    Log.i(TAG, "Файл не удален");
                }
            }
        }
        cursor.close();
    }
}
