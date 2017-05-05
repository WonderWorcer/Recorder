package com.recoder.recoder.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.recoder.recoder.Recognizer.GoogleResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.recoder.recoder.Recognizer.*;
import com.recoder.recoder.Tools.*;


public class AsyncTasker extends AsyncTask<String, Void, Void> {


    @Override
    public Void doInBackground(String... strings) {
        List<String> responses = new ArrayList<String>();
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
            HashMap<String, Integer> words = new HashMap<String, Integer>();
            words.put("бомба", 5);
            words.put("бомбу", 1);
            words.put("Аллах", 1);
            SearchSubString sss = new SearchSubString();
            for (int i = 0; i < responses.size(); i++) {
                if (sss.result(responses.get(i), words) > 5) {
                    //todo action
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }
}