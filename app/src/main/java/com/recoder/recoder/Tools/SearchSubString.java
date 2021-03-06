package com.recoder.recoder.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 17 мая 2017
 * <p>
 * Класс, реализующий поиск в записи по библиотекам
 */

public class SearchSubString {
    Context context = App.getContext();
    DBHelper dbHelper = new DBHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    /**
     * Метод, реализующий поиск слов по фильтру
     *
     * @param response текстовая интерпритация разговора
     * @param filter   фильтр (Название библиотеки)
     * @param _id      уникальный номер записи из база данных
     * @return Результат проверки по выбранному фильтру
     */
    public int checkFilter(String response, String filter, String _id) {
        int result = 0;
        String splitFilter[] = filter.split("_");
        Cursor c = db.query(filter, new String[]{dbHelper.KEY_WORD, dbHelper.VALUE_WORD}, null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            int keyWordColIndex = c.getColumnIndex(dbHelper.KEY_WORD);
            int valueWordColIndex = c.getColumnIndex(dbHelper.VALUE_WORD);

            do {
                String s = c.getString(keyWordColIndex);
                if (response.contains(c.getString(keyWordColIndex))) {
                    result += c.getInt(valueWordColIndex);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(dbHelper.FILTER_NAME, filter);
                    contentValues.put(dbHelper.KEY_WORD, c.getString(keyWordColIndex));
                    contentValues.put(dbHelper.VALUE_WORD, c.getInt(valueWordColIndex));
                    contentValues.put(dbHelper.WORDS_ON_RECORD, _id);
                    db.insert(dbHelper.TABLE_USED_WORDS, null, contentValues);
                }
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Метод, возвращающий результаты проверки записи по словам из библиотеки
     * @param response текстовая интерпритация разговора
     * @param _id - уникальный номер записи из база данных
     * @return возвращает массив результатов проверки
     */
    public int[] result(String response, String _id) {
        int result[] = new int[7];
        result[0] = checkFilter(response, dbHelper.TABLE_DRAG_WORDS, _id);
        result[1] = checkFilter(response, dbHelper.TABLE_EXTREMIST_WORDS, _id);
        result[2] = checkFilter(response, dbHelper.TABLE_THEFT_WORDS, _id);
        result[3] = checkFilter(response, dbHelper.TABLE_PROFANITY_WORDS, _id);
        result[4] = checkFilter(response, dbHelper.TABLE_STATE_SECRET_WORDS, _id);
        result[5] = checkFilter(response, dbHelper.TABLE_BANK_SECRET_WORDS, _id);
        result[6] = checkFilter(response, dbHelper.TABLE_USER_DICTIONARY_WORDS, _id);
        return result;
    }
}
