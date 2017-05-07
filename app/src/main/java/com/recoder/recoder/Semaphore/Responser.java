package com.recoder.recoder.Semaphore;

import android.content.ContentValues;
import android.content.Context;
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
 * Created by Роман on 07.05.2017.
 */

public class Responser extends Thread {
    Semaphore sem;
    String _id;
    String recordPath;
    List<String> responses = new ArrayList<String>();
    Context context = App.getContext();
    public String PREF_API_KEY = "PrefApiKey";
    Recognizer recognizer = new Recognizer(Recognizer.Languages.RUSSIAN, PrefsHelper.readPrefString(context, PREF_API_KEY));
    int[][] finalyResult = new int[6][6];

    Responser(Semaphore sem, String _id, String recordPath) {
        this.sem = sem;
        this._id = _id;
        this.recordPath = recordPath;
    }

    public void run() {
        try {

            //Запрашиваем у семафора разрешение на выполнение
            sem.acquire();


            try {
                File file = new File(recordPath);
                GoogleResponse response = recognizer.getRecognizedDataForAmr(file);
                responses.add(response.getResponse());
                for (String s : response.getOtherPossibleResponses()) {
                    responses.add(s);
                }
                SearchSubString sss = new SearchSubString();
                for (int i = 0; i < responses.size(); i++) {
                    //todo check all files
                    finalyResult[i] = sss.result(responses.get(i));
                }

                for (int k = 0; k < 5; k++)
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 6; j++) {
                            if (finalyResult[i][j] < finalyResult[i + 1][j]) {
                                int temp = finalyResult[i][j];
                                finalyResult[i][j] = finalyResult[i + 1][j];
                                finalyResult[i + 1][j] = temp;
                            }
                        }
                    }

                DBHelper dbHelper = new DBHelper(context);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(dbHelper.DRAG_FILTER, Integer.toString(finalyResult[0][0]));
                contentValues.put(dbHelper.EXTREMIST_FILTER, Integer.toString(finalyResult[0][1]));
                contentValues.put(dbHelper.THEFT_FILTER, Integer.toString(finalyResult[0][2]));
                contentValues.put(dbHelper.PROFANITY_FILTER, Integer.toString(finalyResult[0][3]));
                contentValues.put(dbHelper.STATE_SECRET_FILTER, Integer.toString(finalyResult[0][4]));
                contentValues.put(dbHelper.BANK_SECRET_FILTER, Integer.toString(finalyResult[0][5]));
                contentValues.put(dbHelper.RECORD_STATUS, "Checked");

                db.update(dbHelper.TABLE_RECORDS, contentValues, "_id = ?", new String[]{_id});


                sem.release();

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } catch (InterruptedException e) {
        }
    }
}
