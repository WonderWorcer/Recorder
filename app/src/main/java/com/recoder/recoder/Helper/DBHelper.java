package com.recoder.recoder.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1; ///< Версия базы данных
    public static final String DATABASE_NAME = "recognitionDb"; ///< Имя базы данных

    public static final String TABLE_DRAG_WORDS = "dragWords"; ///< Имя таблицы, содержащая список слов по фильтру "Наркотики"
    public static final String TABLE_EXTREMIST_WORDS = "extremistWords"; ///< Имя таблицы, содержащая список слов по фильтру "Экстремизм"
    public static final String TABLE_THEFT_WORDS = "theftWords"; ///< Имя таблицы, содержащая список слов по фильтру "Воровство"
    public static final String TABLE_PROFANITY_WORDS = "profanityWords"; ///< Имя таблицы, содержащая список слов по фильтру "Нецензурная речь"
    public static final String TABLE_STATE_SECRET_WORDS = "stateSecretWords"; ///< Имя таблицы, содержащая список слов по фильтру "Государственная тайна"
    public static final String TABLE_BANK_SECRET_WORDS = "bankSecretWords"; ///< Имя таблицы, содержащая список слов по фильтру "Банковская тайна"
    public static final String TABLE_USER_DICTIONARY_WORDS = "userDictionaryWords";///<Имя таблицы, содержащая список слов пользовательской библиотеки

    public static final String KEY_ID = "_id"; ///< Первичный ключ любой таблицы
    public static final String KEY_WORD = "keyWord"; ///< Поле для слова
    public static final String VALUE_WORD = "valueWord"; ///< Значение слова

    public static final String TABLE_RECORDS = "records"; ///< Имя таблицы, содержащая список всех записей разговоров
    public static final String RECORD_PATH = "recordPath"; ///< Поле таблицы, характеризующее путь до файла
    public static final String PHONE_NUMBER = "phoneNumber"; ///<Поле таблицы, характеризующее номер телефона
    public static final String SEED = "seed";///<Поле таблицы, характеризующий тип вызова(исходящий, входящий)
    public static final String CALLTIME = "callTime"; ///<Поле таблицы, характеризующее время вызова
    public static final String CALLDATE = "callDate"; ///<Поле таблицы, характеризующее дату вызова
    public static final String RECORD_STATUS = "recordStatus"; ///< Поле таблицы, характеризующее статус проверки файла
    public static final String DRAG_FILTER = "dragFilter"; ///< Поле таблицы, характеризующее значение наркотического фильтра
    public static final String EXTREMIST_FILTER = "extremistFilter"; ///< Поле таблицы, характеризующее значение экстремистского фильтра
    public static final String THEFT_FILTER = "theftFilter"; ///< Поле таблицы, характеризующее значение фильтра воровства
    public static final String PROFANITY_FILTER = "profanityFilter"; ///< Поле таблицы, характеризующее значение нецензурного фильтра
    public static final String STATE_SECRET_FILTER = "stateSecretFilter"; ///< Поле таблицы, характеризующее значение фильтра государственной тайны
    public static final String BANK_SECRET_FILTER = "bankSecretFilter"; ///< Поле таблицы, характеризующее значение фильтра банковской тайны
    public static final String USER_DICTIONARY_FILTER = "userDictionaryFilter";///<Поле таблицы, характеризующее значение фильтра пользовательской библиотеки

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_DRAG_WORDS + "(" + KEY_ID +
                " integer primary key," + KEY_WORD + " text," + VALUE_WORD + " integer" + ")");

        db.execSQL("create table " + TABLE_EXTREMIST_WORDS + "(" + KEY_ID +
                " integer primary key," + KEY_WORD + " text," + VALUE_WORD + " integer" + ")");

        db.execSQL("create table " + TABLE_THEFT_WORDS + "(" + KEY_ID +
                " integer primary key," + KEY_WORD + " text," + VALUE_WORD + " integer" + ")");

        db.execSQL("create table " + TABLE_PROFANITY_WORDS + "(" + KEY_ID +
                " integer primary key," + KEY_WORD + " text," + VALUE_WORD + " integer" + ")");

        db.execSQL("create table " + TABLE_STATE_SECRET_WORDS + "(" + KEY_ID +
                " integer primary key," + KEY_WORD + " text," + VALUE_WORD + " integer" + ")");

        db.execSQL("create table " + TABLE_BANK_SECRET_WORDS + "(" + KEY_ID +
                " integer primary key," + KEY_WORD + " text," + VALUE_WORD + " integer" + ")");

        db.execSQL("create table " + TABLE_USER_DICTIONARY_WORDS + "(" + KEY_ID +
                " integer primary key," + KEY_WORD + " text," + VALUE_WORD + " integer" + ")");

        db.execSQL("create table " + TABLE_RECORDS + "(" + KEY_ID +
                " integer primary key," + RECORD_PATH + " text," + PHONE_NUMBER + " text,"
                + SEED + " text,"  + CALLTIME + " text," + CALLDATE+ " text,"
                + RECORD_STATUS + " text," + DRAG_FILTER + " integer,"
                + EXTREMIST_FILTER + " integer," + THEFT_FILTER + " integer,"
                + PROFANITY_FILTER + " integer," + STATE_SECRET_FILTER + " integer,"
                + USER_DICTIONARY_FILTER + " integer,"
                + BANK_SECRET_FILTER + " integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_DRAG_WORDS);
        db.execSQL("drop table if exists " + TABLE_EXTREMIST_WORDS);
        db.execSQL("drop table if exists " + TABLE_THEFT_WORDS);
        db.execSQL("drop table if exists " + TABLE_PROFANITY_WORDS);
        db.execSQL("drop table if exists " + TABLE_STATE_SECRET_WORDS);
        db.execSQL("drop table if exists " + TABLE_BANK_SECRET_WORDS);
        db.execSQL("drop table if exists " + TABLE_USER_DICTIONARY_WORDS);
        db.execSQL("drop table if exists " + TABLE_RECORDS);
        onCreate(db);
    }
}