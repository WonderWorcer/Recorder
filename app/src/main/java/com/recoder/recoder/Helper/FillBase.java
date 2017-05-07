package com.recoder.recoder.Helper;

/**
 * Created by Роман on 30.03.2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.recoder.recoder.App;

public class FillBase {
    DBHelper dbHelper = new DBHelper(App.getContext());
    SQLiteDatabase database = dbHelper.getWritableDatabase();

    public void addWordOnBase(String table, String word, int prioritet) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.KEY_WORD, word);
        contentValues.put(dbHelper.VALUE_WORD, prioritet);
        database.insert(table, null, contentValues);
    }

    public void FillExtremistWords(Context context) {
        database.delete(dbHelper.TABLE_EXTREMIST_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "бомба", 8);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "взрыв", 8);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "взорв", 8);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "подорв", 8);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "залож", 4);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "пакет", 2);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "уничтож", 8);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "аллах", 7);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "Аллах", 7);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "во имя Аллаха", 8);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "убей", 10);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "убийство", 10);
    }

    public void FillDragWords(Context context) {

        database.delete(dbHelper.TABLE_DRAG_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "доза", 3);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "герыч", 8);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "афганс", 8);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "серого", 4);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "ширнут", 8);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "марихуана", 10);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "героин", 10);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "насик", 8);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "анаша", 10);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "кокс", 8);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "кекс", 4);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "кокаин", 10);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "морфий", 6);
    }

    public void FillStateSecretWords(Context context) {

        database.delete(dbHelper.TABLE_STATE_SECRET_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_STATE_SECRET_WORDS, "код ракеты", 7);
    }

    public void FillTheftWords(Context context) {

        database.delete(dbHelper.TABLE_THEFT_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "воров", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "свиснуть", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "своров", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "вор", 3);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "присво", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "похищ", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "кража", 8);

    }

    public void FillProfanityWords(Context context) {

        database.delete(dbHelper.TABLE_PROFANITY_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_PROFANITY_WORDS, "432543543543", 8);

    }

    public void FillBankSecretWords(Context context) {

        database.delete(dbHelper.TABLE_BANK_SECRET_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_BANK_SECRET_WORDS, "4365464765", 8);
    }
}