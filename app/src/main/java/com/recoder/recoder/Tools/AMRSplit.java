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
 * Created by Роман on 04.05.2017.
 */

public class AMRSplit {


    public static final long floppySize = (long) (1.4 * 1024 * 1024);
    /**
     * the maximum size of each file "chunk" generated, in bytes
     */
    public int frameSize;
    public long chunkSize = (long) (6000);
    Context context;
    public AMRSplit(Context context){
    this.context = context;
}
    DBHelper dbHelper = new DBHelper(context);
    public void initv1() throws IOException {

        String ss = "Record1.amr";

        try {
            //split(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void split(String filename) throws FileNotFoundException, IOException {

        // open the file
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        File inputFile = new File(filename);
        try {
            FileInputStream fis = new FileInputStream(inputFile);

            byte fileContent[]= new byte[(int) inputFile.length()];

            fis.read(fileContent); // Reads the file content as byte.
            fis.close();
            switch ((fileContent[7]>>3)& 0x0F) {
                case 0:frameSize = 13;break;
                case 1:frameSize = 14;break;
                case 2:frameSize = 16;break;
                case 3:frameSize = 18;break;
                case 4:frameSize = 20;break;
                case 5:frameSize = 21;break;
                case 6:frameSize = 27;break;
                case 7:frameSize = 32;break;
            }
            chunkSize *= frameSize;
            }
            catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();

        }
        // get the file length
        File f = new File(filename);
        long fileSize = f.length();
        byte fileContent[] = new byte[7];
        fileContent[0] = 35;
        fileContent[1] = 33;
        fileContent[2] = 65;
        fileContent[3] = 77;
        fileContent[4] = 82;
        fileContent[5] = 10;
        // loop for each full chunk
        int subfile;

        for (subfile = 0; subfile < fileSize / chunkSize; subfile++) {
            // open the output file
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("part"+subfile + filename));

            if (subfile > 0) {
                for (int i = 0; i < 6; i++) {
                    out.write(fileContent[i]);
                }
            }

            if (subfile == 0) {
                for (int currentByte = 0; currentByte < chunkSize + 6; currentByte++) {
                    // load one byte from the input file and write it to the output file
                    out.write(in.read());
                }
            } else // write the right amount of bytes
            {
                for (int currentByte = 0; currentByte < chunkSize; currentByte++) {
                    // load one byte from the input file and write it to the output file
                    out.write(in.read());
                }
            }

            // close the file
            out.close();

            ContentValues newValues = new ContentValues();
            newValues.put(dbHelper.RECORD_PATH,"part"+subfile + filename);
            newValues.put(dbHelper.PHONE_NUMBER,filename);
            newValues.put(dbHelper.SEED,filename);
            newValues.put(dbHelper.CALLTIME,"1:00");
            newValues.put(dbHelper.CALLDATE,filename);
            newValues.put(dbHelper.RECORD_STATUS,"Not Checked");
            newValues.put(dbHelper.DRAG_FILTER,"0");
            newValues.put(dbHelper.EXTREMIST_FILTER,"0");
            newValues.put(dbHelper.THEFT_FILTER,"0");
            newValues.put(dbHelper.PROFANITY_FILTER,"0");
            newValues.put(dbHelper.STATE_SECRET_FILTER,"0");
            newValues.put(dbHelper.BANK_SECRET_FILTER,"0");
            db.insert(dbHelper.TABLE_RECORDS,null,newValues);
        }

        // loop for the last chunk (which may be smaller than the chunk size)
        if (fileSize != chunkSize * (subfile - 1)) {
            // open the output file
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("part"+subfile + filename));

            // write the rest of the file
            int b;
            if (subfile > 0) {
                for (int i = 0; i < 6; i++) {
                    out.write(fileContent[i]);
                }
            }
            while ((b = in.read()) != -1) {
                out.write(b);
            }

            // close the file
            out.close();
            ContentValues newValues = new ContentValues();
            newValues.put(dbHelper.RECORD_PATH,"part"+subfile + filename);
            newValues.put(dbHelper.PHONE_NUMBER,filename);
            newValues.put(dbHelper.SEED,filename);
            newValues.put(dbHelper.CALLTIME,"1:00");
            newValues.put(dbHelper.CALLDATE,filename);
            newValues.put(dbHelper.RECORD_STATUS,"Not Checked");
            newValues.put(dbHelper.DRAG_FILTER,"0");
            newValues.put(dbHelper.EXTREMIST_FILTER,"0");
            newValues.put(dbHelper.THEFT_FILTER,"0");
            newValues.put(dbHelper.PROFANITY_FILTER,"0");
            newValues.put(dbHelper.STATE_SECRET_FILTER,"0");
            newValues.put(dbHelper.BANK_SECRET_FILTER,"0");
            db.insert(dbHelper.TABLE_RECORDS,null,newValues);
        }

        // close the file
        in.close();
    }

}
