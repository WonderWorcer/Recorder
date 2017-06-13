package com.recoder.recoder.fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.MainActivity;
import com.recoder.recoder.R;
import com.recoder.recoder.Tools.NumberInformation;
import com.recoder.recoder.adapters.rvAnalyzeRecords;
import com.recoder.recoder.models.analyzeRecords;
import com.recoder.recoder.models.words;

import java.util.ArrayList;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс необходимый для иницилизации фрагмента
 */
public class fragmentAnalyzeRecords extends Fragment {

    Toolbar toolbar;
    DBHelper dbHelper = new DBHelper(App.getContext());
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<analyzeRecords> list = new ArrayList<>();
    @Override
    /**
     * Инициализация фрагмента
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_analyze_records, container, false);

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Анализ по записям");

        Drawable mDrawable = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.ic_dehaze_24dp);
        toolbar.setNavigationIcon(mDrawable);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_records);

        toolbar.inflateMenu(R.menu.drawer_view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        NumberInformation numberInformation = new NumberInformation();
        Cursor c = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.PHONE_NUMBER, dbHelper.KEY_ID}, null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {


            int phoneNumberColIndex = c.getColumnIndex(dbHelper.PHONE_NUMBER);
            int keyIdColIndex = c.getColumnIndex(dbHelper.KEY_ID);
            do {
                analyzeRecords item = new analyzeRecords();
                ArrayList<words> liist = new ArrayList<>();
                if (numberInformation.getContactName(c.getString(phoneNumberColIndex)) != null)
                    item.setName(numberInformation.getContactName(c.getString(phoneNumberColIndex)));
                else
                    item.setName(c.getString(phoneNumberColIndex));


                Cursor cursor = db.query(dbHelper.TABLE_USED_WORDS,
                        new String[]{dbHelper.KEY_WORD, dbHelper.VALUE_WORD, dbHelper.FILTER_NAME},
                        dbHelper.WORDS_ON_RECORD + "= ?",
                        new String[]{c.getString(keyIdColIndex)}, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int filterNameColIndex = cursor.getColumnIndex(dbHelper.FILTER_NAME);
                    int keyWordColIndex = cursor.getColumnIndex(dbHelper.KEY_WORD);
                    int valueWordColIndex = cursor.getColumnIndex(dbHelper.VALUE_WORD);

                    do {
                        words word = new words();
                        word.setWord(cursor.getString(keyWordColIndex));
                        word.setPriority(cursor.getInt(valueWordColIndex));
                        word.setFilterName(cursor.getString(filterNameColIndex));
                        liist.add(word);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
                item.setPriority(15);
                item.setListOfWords(liist);
                list.add(item);

            } while (c.moveToNext());
            c.close();
        }




        try {
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new rvAnalyzeRecords(list, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
        }


}

