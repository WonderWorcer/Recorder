package com.recoder.recoder.Helper;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 30 апреля 2017
 * <p>
 * Класс, реализующий добавление слов и их приоритетов в соответствующие библиотеки
 */

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.recoder.recoder.App;

public class FillBase {
    DBHelper dbHelper = new DBHelper(App.getContext());
    SQLiteDatabase database = dbHelper.getWritableDatabase();

    /**
     * Метод, добавляющий слово и его приоритет в соответствующую таблицу
     *
     * @param table     таблица, в которую необходимо добавить слово
     * @param word      слово, которое нужно добавить в библиотеку
     * @param prioritet приоритет слова, которое нужно добавить в библиотеку
     */
    public void addWordOnBase(String table, String word, int prioritet) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.KEY_WORD, word);
        contentValues.put(dbHelper.VALUE_WORD, prioritet);
        database.insert(table, null, contentValues);
    }

    /**
     * Метод, инициализируюзий добавление слов в библиотеки
     */
    public void fillAllBase() {
        FillExtremistWords();
        FillDragWords();
        FillStateSecretWords();
        FillTheftWords();
        FillProfanityWords();
        FillBankSecretWords();
    }

    /**
     * Метод, добавляющий слова в библиотеку Экстремистских слов
     */
    public void FillExtremistWords() {
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
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "фсб", 6);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "мвд", 6);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "террорист", 10);
        addWordOnBase(dbHelper.TABLE_EXTREMIST_WORDS, "акбар", 6);
    }

    /**
     * Метод, добавляющий слова в библиотеку слов, связанных с наркотиками
     */
    public void FillDragWords() {

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
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "нацвай", 8);
        addWordOnBase(dbHelper.TABLE_DRAG_WORDS, "насик", 5);
    }

    /**
     * Метод, добавляющий слова в библиотеку слов, связанных с государственной тайной
     */
    public void FillStateSecretWords() {

        database.delete(dbHelper.TABLE_STATE_SECRET_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_STATE_SECRET_WORDS, "код ракеты", 7);
        addWordOnBase(dbHelper.TABLE_STATE_SECRET_WORDS, "Штаб", 4);
        addWordOnBase(dbHelper.TABLE_STATE_SECRET_WORDS, "Навальный", 7);
        addWordOnBase(dbHelper.TABLE_STATE_SECRET_WORDS, "Навэльный", 7);
    }

    /**
     * Метод, добавляющий слова в библиотеку слов, связанных с воровством
     */
    public void FillTheftWords() {

        database.delete(dbHelper.TABLE_THEFT_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "воров", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "свиснуть", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "своров", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "вор", 3);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "присво", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "похищ", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "кража", 8);
        addWordOnBase(dbHelper.TABLE_THEFT_WORDS, "красть", 8);

    }

    /**
     * Метод, добавляющий слова в библиотеку нецензурных слов
     */
    public void FillProfanityWords() {

        database.delete(dbHelper.TABLE_PROFANITY_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_PROFANITY_WORDS, "мудак", 8);
        addWordOnBase(dbHelper.TABLE_PROFANITY_WORDS, "редиска", 8);
        addWordOnBase(dbHelper.TABLE_PROFANITY_WORDS, "антон", 8);
        addWordOnBase(dbHelper.TABLE_PROFANITY_WORDS, "муха", 8);
    }

    /**
     * Метод, добавляющий слова в библиотеку слов, связанных с банковской тайной
     */
    public void FillBankSecretWords() {

        database.delete(dbHelper.TABLE_BANK_SECRET_WORDS, null, null);
        addWordOnBase(dbHelper.TABLE_BANK_SECRET_WORDS, "номер операции", 8);
        addWordOnBase(dbHelper.TABLE_BANK_SECRET_WORDS, "пароль", 8);
    }
}