package com.recoder.recoder.Tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;

public class SearchSubString {
    Context context = App.getContext();
    DBHelper dbHelper = new DBHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    public int checkFilter(String response, String filter) {
        int result = 0;
        Cursor c = db.query(filter, new String[]{dbHelper.KEY_WORD, dbHelper.VALUE_WORD}, null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            int keyWordColIndex = c.getColumnIndex(dbHelper.KEY_WORD);
            int valueWordColIndex = c.getColumnIndex(dbHelper.VALUE_WORD);
            do {

                if (response.contains(c.getString(keyWordColIndex))) {
                    result += c.getInt(valueWordColIndex);
                }
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    public int[] result(String response) {
        int result[] = new int[6];

        result[0] = checkFilter(response, dbHelper.TABLE_DRAG_WORDS);
        result[1] = checkFilter(response, dbHelper.TABLE_EXTREMIST_WORDS);
        result[2] = checkFilter(response, dbHelper.TABLE_THEFT_WORDS);
        result[3] = checkFilter(response, dbHelper.TABLE_PROFANITY_WORDS);
        result[4] = checkFilter(response, dbHelper.TABLE_STATE_SECRET_WORDS);
        result[5] = checkFilter(response, dbHelper.TABLE_BANK_SECRET_WORDS);

        return result;
    }
}
