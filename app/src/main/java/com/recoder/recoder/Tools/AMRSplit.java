package com.recoder.recoder.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.recoder.recoder.Helper.DBHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 0.5
 * \date 5 марта 2017
 * <p>
 * Класс, реализующий разделение amr файла на части
 * длительностью до 1 минуты
 */

public class AMRSplit {

    public int frameSize; ///< Размер одного кадра
    public long chunkSize = (long) (6000); ///< Максимальный размер каждого «куска» файла, сгенерированного в байтах
    Context context;
    DBHelper dbHelper;

    /**
     * Конструктор
     *
     * @param context текущий контекст приложения
     */
    public AMRSplit(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    /**
     * Разделяет amr запись на фрагменты длительностью до
     * 1 минуты и записывает все необходиммые данные о записе
     * в базу данных
     *
     * @param filename amr - файл
     * @throws FileNotFoundException Исключение при отсутствии файла
     * @throws IOException
     */
    public void split(File filename) throws FileNotFoundException, IOException {

        // Необходимо для получения папки, в которой лежит файл
        final File parentFolder = new File(filename.getAbsolutePath()
                .substring(0, filename.getAbsolutePath().lastIndexOf(
                        File.separator)));

        String path = parentFolder.getAbsolutePath();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename.getAbsolutePath()));
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        File inputFile = new File(filename.getAbsolutePath());
        try {
            FileInputStream fis = new FileInputStream(inputFile);

            byte fileContent[] = new byte[(int) inputFile.length()];

            fis.read(fileContent); // Чтение файла по-байтово
            fis.close();
            // Вычисление размера кадра
            switch ((fileContent[7] >> 3) & 0x0F) {
                case 0:
                    frameSize = 13;
                    break;
                case 1:
                    frameSize = 14;
                    break;
                case 2:
                    frameSize = 16;
                    break;
                case 3:
                    frameSize = 18;
                    break;
                case 4:
                    frameSize = 20;
                    break;
                case 5:
                    frameSize = 21;
                    break;
                case 6:
                    frameSize = 27;
                    break;
                case 7:
                    frameSize = 32;
                    break;
            }
            chunkSize *= frameSize;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        File f = new File(filename.getAbsolutePath());
        //Расшипление строки, в которой содержится вся необходимая информация о записи
        String splitString[] = filename.getName().split("_");
        // Получение длины файла
        long fileSize = f.length();
        byte fileContent[] = new byte[7];
        fileContent[0] = 35;
        fileContent[1] = 33;
        fileContent[2] = 65;
        fileContent[3] = 77;
        fileContent[4] = 82;
        fileContent[5] = 10;
        //Цикл для каждого полного фрагмента
        int subfile;

        for (subfile = 0; subfile < fileSize / chunkSize; subfile++) {
            // Открываем выходной файл
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path + "/" + "part" + subfile + filename.getName()));

            if (subfile > 0) {
                for (int i = 0; i < 6; i++) {
                    out.write(fileContent[i]);
                }
            }

            if (subfile == 0) {
                for (int currentByte = 0; currentByte < chunkSize + 6; currentByte++) {
                    //Загружаем один байт из входного файла и записываем его в выходной файл
                    out.write(in.read());
                }
            } else
            {
                for (int currentByte = 0; currentByte < chunkSize; currentByte++) {
                    // Загрузить один байт из входного файла и записать его в выходной файл
                    out.write(in.read());
                }
            }

            // закрываем файл
            out.close();
            //Записываем запись в базу данных
            ContentValues newValues = new ContentValues();
            newValues.put(dbHelper.RECORD_PATH, path + "/" + "part" + subfile + filename.getName());
            newValues.put(dbHelper.PHONE_NUMBER, splitString[2]);
            newValues.put(dbHelper.SEED, splitString[3]);
            newValues.put(dbHelper.CALLTIME, "1:00");
            newValues.put(dbHelper.CALLDATE, splitString[1]);
            newValues.put(dbHelper.RECORD_STATUS, "Not Checked");
            newValues.put(dbHelper.DRAG_FILTER, "0");
            newValues.put(dbHelper.EXTREMIST_FILTER, "0");
            newValues.put(dbHelper.THEFT_FILTER, "0");
            newValues.put(dbHelper.PROFANITY_FILTER, "0");
            newValues.put(dbHelper.STATE_SECRET_FILTER, "0");
            newValues.put(dbHelper.BANK_SECRET_FILTER, "0");
            db.insert(dbHelper.TABLE_RECORDS, null, newValues);
        }

        // Цикл для последнего фрагмента (который может быть меньше размера фрагмента)
        if (fileSize != chunkSize * (subfile - 1)) {
            // Открываем выходной файл
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path + "/" + "part" + subfile + filename.getName()));

            // пишем остальную часть файла
            int b;
            if (subfile > 0) {
                for (int i = 0; i < 6; i++) {
                    out.write(fileContent[i]);
                }
            }
            while ((b = in.read()) != -1) {
                out.write(b);
            }

            // Закрываем файл
            out.close();
            //Записываем запись в базу данных
            ContentValues newValues = new ContentValues();
            newValues.put(dbHelper.RECORD_PATH, path + "/" + "part" + subfile + filename.getName());
            newValues.put(dbHelper.PHONE_NUMBER, splitString[2]);
            newValues.put(dbHelper.SEED, splitString[3]);
            newValues.put(dbHelper.CALLTIME, "<1мин");
            newValues.put(dbHelper.CALLDATE, splitString[1]);
            newValues.put(dbHelper.RECORD_STATUS, "NotChecked");
            newValues.put(dbHelper.DRAG_FILTER, "0");
            newValues.put(dbHelper.EXTREMIST_FILTER, "0");
            newValues.put(dbHelper.THEFT_FILTER, "0");
            newValues.put(dbHelper.PROFANITY_FILTER, "0");
            newValues.put(dbHelper.STATE_SECRET_FILTER, "0");
            newValues.put(dbHelper.BANK_SECRET_FILTER, "0");
            db.insert(dbHelper.TABLE_RECORDS, null, newValues);
        }

        // Закрываем файл
        in.close();
    }

}
