package com.recoder.recoder;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.Semaphore;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс, для работы с константами,
 * активацией семафора в любой точке программы,
 * хранения номера с кем идет диалог
 * и для работы с контекстом MainActivity в любой точке программы
 */

public class App extends Application {

    private static Context Context; ///<Контекст MainActivity
    private static String phoneNumber; ///<Номер телефона, с кем идет диаглог
    private static Semaphore semaphore; ///<Экземпляр семафора, необходим для запуска изолированного потока в любой точке программы
    private static CallRecord callRecord; ///<Экземпляр класса CallRecord, необходим для правильной работы некоторых функций приложения
    public final static String PREF_PASSWORD = "prefPassword"; ///<Графический пароль для входа в приложение
    public final static String PREF_PASSWORD_ACTIVE = "PrefPasswordActive";///<Флаг установки графического пароля
    public final static String PREF_AUTOFILLBASE = "prefAutoFillBase";///<Флаг автоматического заполнения базы слов
    public final static String PREF_CHANGE_PASSWORD = "prefChangePassword";///<Флаг возможности редактирования графического пароля
    public final static String PREF_DELETE_AFTER_10_ATTEMPT = "prefDeleteAfter10Attempt";///<Флаг удаления всех файлов после 10 неудачных попыток
    public static final String PREF_AUTO_DELETE = "PrefAutoDelete";///<константа для сохранения времени жизни файлов
    public final static String PREF_API_KEY = "PrefApiKey";///<API ключ для распознования речи GoogleRecognition
    public static final String PREF_DIR_PATH = "PrefDirPath";///<константа для сохранения пути до директории
    public final static String PREF_ALL_FILES = "prefAllFiles";///<Константа, для подсчета всех записей
    public final static String PREF_ANALIZED_FILES = "prefAnalizedFiles";///<Константа, для подсчета всех проанализированных файлов
    public final static String PREF_ISRECORDING = "prefIsRecording";///<Флаг записи (Запись включена, выключена)
    public static final String GOOGLE_RECOGNIZER_URL = "http://www.google.com/speech-api/v2/recognize?client=chromium&output=json";///< URL для POST запроса на сервис GoogleRecognition
    public static final String PREF_DEFAULT_FOLDER = "CallRecord";///<Константа, для хранения директории по умолчанию
    public static CallRecord getCallRecord() {
        return callRecord;
    }

    public static void setCallRecord(CallRecord mCallRecord) {
        callRecord = mCallRecord;
    }

    public static Context getContext() {
        return Context;
    }

    public static void setSemaphore(Semaphore mSemaphore) {
        semaphore = mSemaphore;
    }

    public static Semaphore getSemaphore() {
        return semaphore;
    }

    public static void setContext(Context mContext) {
        Context = mContext;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        App.phoneNumber = phoneNumber;
    }
}