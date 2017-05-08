package com.recoder.recoder.AsyncTask;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.Recognizer.GoogleResponse;
import com.recoder.recoder.Recognizer.Recognizer;
import com.recoder.recoder.Tools.SearchSubString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AsyncTasker extends AsyncTask<String, Void, Void> {


    @Override
    public Void doInBackground(String... strings) {
        List<String> responses = new ArrayList<String>();

        int[][] finalyResult = new int[6][6];
        //callRecord.getRecordDirPath()+"/"+callRecord.getRecordDirName()+"/"+"Record1.amr"
        for (String s2 : strings) {
            File file = new File(s2);
            //Name your file whatever you want

            Recognizer recognizer = new Recognizer(Recognizer.Languages.RUSSIAN, "AIzaSyCvfglaj2kcmjzNY5kyItkBx5wsHXQm8Y4");


            try {
                GoogleResponse response = recognizer.getRecognizedDataForAmr(file);
                responses.add(response.getResponse());
                for (String s : response.getOtherPossibleResponses()) {
                    responses.add(s);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            SearchSubString sss = new SearchSubString();
            for (int i = 0; i < responses.size(); i++) {
                //todo check all files
                // finalyResult[i] = sss.result(responses.get(i));
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
            Context context = App.getContext();
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

            // db.update(dbHelper.TABLE_RECORDS, contentValues, "id = ?", new String[]{_id});


        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }
}