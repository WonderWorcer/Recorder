package com.recoder.recoder.Helper;

/**
 * Created by Роман on 30.03.2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FillBase {

    public void FillExtremistWords(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.KEY_WORD, "Бомба");
        contentValues.put(dbHelper.VALUE_WORD, 5);
        database.insert(dbHelper.TABLE_DRAG_WORDS, null, contentValues);
    }

    public void FillDragWords(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.KEY_WORD, "Герыч");
        contentValues.put(dbHelper.VALUE_WORD, 5);
        database.insert(dbHelper.TABLE_EXTREMIST_WORDS, null, contentValues);
    }

    public void FillStateSecretWords(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.KEY_WORD, "На на на");
        contentValues.put(dbHelper.VALUE_WORD, 5);
        database.insert(dbHelper.TABLE_THEFT_WORDS, null, contentValues);
    }

    public void FillTheftWords(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.KEY_WORD, "Своровать");
        contentValues.put(dbHelper.VALUE_WORD, 5);
        database.insert(dbHelper.TABLE_PROFANITY_WORDS, null, contentValues);
    }

    public void FillProfanityWords(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.KEY_WORD, "танки на");
        contentValues.put(dbHelper.VALUE_WORD, 5);
        database.insert(dbHelper.TABLE_STATE_SECRET_WORDS, null, contentValues);
    }

    public void FillBankSecretWords(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.KEY_WORD, "украсть деньги");
        contentValues.put(dbHelper.VALUE_WORD, 5);
        database.insert(dbHelper.TABLE_BANK_SECRET_WORDS, null, contentValues);
    }
}