package com.recoder.recoder.Facade;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.Helper.PrefsHelper;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 0.5
 * \date 7 марта 2017 года
 * <p>
 * Класс, реализующий чтение данных из базы
 * данных для заполнения главной таблицы
 */

public class ReadFromDB {

    /**
     * Метод, который получает информацию из базы данных и записывающий значение в контекст
     */
    public void generateMainPage() {
        DBHelper dbHelper = new DBHelper(App.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID}, null, null, null, null, null);
        PrefsHelper.writePrefInt(App.getContext(), App.PREF_ALL_FILES, cursor.getCount());
        cursor.close();

        cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID},
                dbHelper.RECORD_STATUS + "= ?", new String[]{"Checked"}, null, null, null);
        PrefsHelper.writePrefInt(App.getContext(), App.PREF_ANALIZED_FILES, cursor.getCount());
        cursor.close();

        cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID},
                dbHelper.EXTREMIST_FILTER + "> ?", new String[]{"0"}, null, null, null);
        PrefsHelper.writePrefInt(App.getContext(), DBHelper.EXTREMIST_FILTER, cursor.getCount());
        cursor.close();

        cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID},
                dbHelper.DRAG_FILTER + "> ?", new String[]{"0"}, null, null, null);
        PrefsHelper.writePrefInt(App.getContext(), DBHelper.DRAG_FILTER, cursor.getCount());
        cursor.close();

        cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID},
                dbHelper.PROFANITY_FILTER + "> ?", new String[]{"0"}, null, null, null);
        PrefsHelper.writePrefInt(App.getContext(), DBHelper.PROFANITY_FILTER, cursor.getCount());
        cursor.close();

        cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID},
                dbHelper.STATE_SECRET_FILTER + "> ?", new String[]{"0"}, null, null, null);
        PrefsHelper.writePrefInt(App.getContext(), DBHelper.STATE_SECRET_FILTER, cursor.getCount());
        cursor.close();

        cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID},
                dbHelper.THEFT_FILTER + "> ?", new String[]{"0"}, null, null, null);
        PrefsHelper.writePrefInt(App.getContext(), DBHelper.THEFT_FILTER, cursor.getCount());
        cursor.close();

        cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID},
                dbHelper.BANK_SECRET_FILTER + "> ?", new String[]{"0"}, null, null, null);
        PrefsHelper.writePrefInt(App.getContext(), DBHelper.BANK_SECRET_FILTER, cursor.getCount());
        cursor.close();

        cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.KEY_ID},
                dbHelper.USER_DICTIONARY_FILTER + "> ?", new String[]{"0"}, null, null, null);
        PrefsHelper.writePrefInt(App.getContext(), DBHelper.USER_DICTIONARY_FILTER, cursor.getCount());
        cursor.close();

    }

}
