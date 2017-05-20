package com.recoder.recoder.Tools;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.recoder.recoder.App;
import com.recoder.recoder.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 17 мая 2017
 * <p>
 * Класс, реализующий работу с книгой контактов
 */

public class NumberInformation {
    /**
     * Метод, позволяющий идентифицировать имя контакта по номеру телефона
     *
     * @param phoneNumber номер телефона
     * @return имя контакта, соотвествующее номеру телефона
     */
    public static String getContactName(String phoneNumber) {
        ContentResolver cr = App.getContext().getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }


    public static Bitmap retrieveContactPhoto(String number) {
        ContentResolver contentResolver = App.getContext().getContentResolver();
        String contactId = null;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID};

        Cursor cursor =
                contentResolver.query(
                        uri,
                        projection,
                        null,
                        null,
                        null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
            }
            cursor.close();
        }

        Bitmap photo = BitmapFactory.decodeResource(App.getContext().getResources(),
                R.drawable.face);

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(App.getContext().getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactId)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            }

            assert inputStream != null;
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    /**
     * Метод, возвращаемый обработанную дату файла
     * @param date необработанная дата файла
     * @return возвращает обработанную дату файла
     */
    public static String dateOfRecord(String date) {
        String finishDate = "";
        finishDate += Integer.toString(Integer.parseInt(date.substring(8, 10))) + ":" + date.substring(10, 12) + ", ";
        finishDate += Integer.toString(Integer.parseInt(date.substring(0, 2)));
        switch (Integer.parseInt(date.substring(2, 4))) {
            case 1:
                finishDate += " января";
                break;
            case 2:
                finishDate += " февраля";
                break;
            case 3:
                finishDate += " марта";
                break;
            case 4:
                finishDate += " апреля";
                break;
            case 5:
                finishDate += " мая";
                break;
            case 6:
                finishDate += " июня";
                break;
            case 7:
                finishDate += " июля";
                break;
            case 8:
                finishDate += " августа";
                break;
            case 9:
                finishDate += " сентября";
                break;
            case 10:
                finishDate += " октября";
                break;
            case 11:
                finishDate += " ноября";
                break;
            case 12:
                finishDate += " декабря";
                break;
        }
        return finishDate;
    }
}
