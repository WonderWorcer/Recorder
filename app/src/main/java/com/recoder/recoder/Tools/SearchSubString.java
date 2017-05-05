package com.recoder.recoder.Tools;

import android.content.Context;

import java.util.HashMap;

import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.Recognizer.*;
import com.recoder.recoder.MainActivity;

public class SearchSubString {
    Context context;

    public SearchSubString() {

    }


    public int result(String response, HashMap<String, Integer> words) {
        DBHelper dbHelper = new DBHelper(context);
        int result = 0;
        for (String s : words.keySet()) {
            if (response.contains(s)) {
                result += words.get(s);
            }
        }
        return result;
    }
}
