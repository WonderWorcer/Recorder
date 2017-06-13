package com.recoder.recoder.fragments;


import android.content.Context;
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
import com.recoder.recoder.adapters.rvRecords;
import com.recoder.recoder.models.record;

import java.util.ArrayList;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс необходимый для иницилизации фрагмента
 */
public class fragmentRecords extends Fragment {

    Toolbar toolbar;
    Context context = App.getContext();
    NumberInformation numberInformation;
    DBHelper dbHelper = new DBHelper(context);
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<record> list = new ArrayList<>();
    SQLiteDatabase db = dbHelper.getWritableDatabase();


    @Override
    /**
     * Инициализация фрагмента
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_records, container, false);

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Записи");

        Drawable mDrawable = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.ic_dehaze_24dp);
        toolbar.setNavigationIcon(mDrawable);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_records);

        toolbar.inflateMenu(R.menu.drawer_view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openDrawer();
            }
        });

        numberInformation = new NumberInformation();
        Cursor c = db.query(dbHelper.TABLE_RECORDS,
                new String[]{dbHelper.PHONE_NUMBER, dbHelper.SEED, dbHelper.KEY_ID,
                        dbHelper.CALLTIME, dbHelper.CALLDATE, dbHelper.RECORD_PATH},
                null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            int phoneNumberColIndex = c.getColumnIndex(dbHelper.PHONE_NUMBER);
            int seedColIndex = c.getColumnIndex(dbHelper.SEED);
            int idColIndex = c.getColumnIndex(dbHelper.KEY_ID);
            int callTimeColIndex = c.getColumnIndex(dbHelper.CALLTIME);
            int callDateColIndex = c.getColumnIndex(dbHelper.CALLDATE);
            int pathColIndex = c.getColumnIndex(dbHelper.RECORD_PATH);
            do {


                record item = new record();
                if (c.getString(seedColIndex).equals("Исходящий")) {
                    item.setIshod(true);
                } else
                    item.setIshod(false);

                if (numberInformation.getContactName(c.getString(phoneNumberColIndex)) != null)
                    item.setNumber(numberInformation.getContactName(c.getString(phoneNumberColIndex)));
                else
                    item.setNumber(c.getString(phoneNumberColIndex));
                item.setTime(numberInformation.dateOfRecord(c.getString(callDateColIndex)));

                item.set_id(c.getInt(idColIndex));
                item.setTime_call(c.getString(callTimeColIndex));
                item.setPath(c.getString(pathColIndex));
                list.add(item);

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }

        c.close();

        try {
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new rvRecords(list, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

}
