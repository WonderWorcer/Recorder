package com.recoder.recoder.Semaphore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.Helper.PrefsHelper;
import com.recoder.recoder.Recognizer.GoogleResponse;
import com.recoder.recoder.Recognizer.Recognizer;
import com.recoder.recoder.Tools.SearchSubString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 17 мая 2017
 * <p>
 * Класс, реализующий запуск изолированного потока для отправки аудио-файла на сервер.
 */

public class Responser extends Thread {
    Semaphore sem = App.getSemaphore();
    List<String> responses = new ArrayList<String>();
    Context context = App.getContext();
    DBHelper dbHelper = new DBHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Recognizer recognizer = new Recognizer(Recognizer.Languages.RUSSIAN, PrefsHelper.readPrefString(context, App.PREF_API_KEY));
    int[][] finalyResult = new int[7][7];

    Responser() {

    }

    /**
     * Метод, который по-очередно отправляет аудио-файлы на сервер Gooogle Recognition.
     * Работает в бесконечном цикле, зацикливается командной sem.acquire();
     * Цикл автоматически разблокируется при команде sem.release();
     */
    public void run() {
        while (true) {
            try {

                Cursor cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.RECORD_PATH, dbHelper.KEY_ID}
                        , dbHelper.RECORD_STATUS + "= ?", new String[]{"NotChecked"}, null, null, null);
                // ставим позицию курсора на первую строку выборки

                if (cursor.moveToFirst()) {

                    int recordPathColIndex = cursor.getColumnIndex(dbHelper.RECORD_PATH);
                    int idColIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
                    sem.acquire();
                    do {

                        //Запрашиваем у семафора разрешение на выполнение

                        try {
                            File file = new File(cursor.getString(recordPathColIndex));
                            GoogleResponse response = recognizer.getRecognizedDataForAmr(file);
                            responses.add(response.getResponse());
                            for (String s : response.getOtherPossibleResponses()) {
                                responses.add(s);
                            }
                            SearchSubString sss = new SearchSubString();
                            for (int i = 1; i < responses.size(); i++) {
                                //todo check all files
                                finalyResult[i] = sss.result(responses.get(i), cursor.getString(idColIndex));
                            }

                            for (int k = 0; k < 6; k++)
                                for (int i = 0; i < 6; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        if (finalyResult[i][j] < finalyResult[i + 1][j]) {
                                            int temp = finalyResult[i][j];
                                            finalyResult[i][j] = finalyResult[i + 1][j];
                                            finalyResult[i + 1][j] = temp;
                                        }
                                    }
                                }

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(dbHelper.DRAG_FILTER, Integer.toString(finalyResult[0][0]));
                            contentValues.put(dbHelper.EXTREMIST_FILTER, Integer.toString(finalyResult[0][1]));
                            contentValues.put(dbHelper.THEFT_FILTER, Integer.toString(finalyResult[0][2]));
                            contentValues.put(dbHelper.PROFANITY_FILTER, Integer.toString(finalyResult[0][3]));
                            contentValues.put(dbHelper.STATE_SECRET_FILTER, Integer.toString(finalyResult[0][4]));
                            contentValues.put(dbHelper.BANK_SECRET_FILTER, Integer.toString(finalyResult[0][5]));
                            contentValues.put(dbHelper.USER_DICTIONARY_FILTER, Integer.toString(finalyResult[0][6]));
                            contentValues.put(dbHelper.RECORD_STATUS, "Checked");

                            db.update(dbHelper.TABLE_RECORDS, contentValues, "_id = ?", new String[]{cursor.getString(idColIndex)});


                            //sem.release();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } while (cursor.moveToNext());
                }

                cursor.close();
                sem.acquire();
            } catch (InterruptedException e) {
            }
        }
    }
}
