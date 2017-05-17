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
 * A simple {@link Fragment} subclass.
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
                new String[]{dbHelper.PHONE_NUMBER, dbHelper.SEED, dbHelper.KEY_ID, dbHelper.CALLTIME, dbHelper.CALLDATE},
                null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            int phoneNumberColIndex = c.getColumnIndex(dbHelper.PHONE_NUMBER);
            int seedColIndex = c.getColumnIndex(dbHelper.SEED);
            int idColIndex = c.getColumnIndex(dbHelper.KEY_ID);
            int callTimeColIndex = c.getColumnIndex(dbHelper.CALLTIME);
            int callDateColIndex = c.getColumnIndex(dbHelper.CALLDATE);
            do {
                // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");

                record item = new record();
                if (c.getString(seedColIndex).equals("Исходящий")) {
                    item.setIshod(true);
                } else
                    item.setIshod(false);

                if (numberInformation.getContactName(c.getString(phoneNumberColIndex)) != null)
                    item.setNumber(numberInformation.getContactName(c.getString(phoneNumberColIndex)));
                else
                    item.setNumber(c.getString(phoneNumberColIndex));
                item.setTime(c.getString(seedColIndex));
                item.set_id(c.getInt(idColIndex));
                item.setTime_call(c.getString(callTimeColIndex));
                list.add(item);

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }

        c.close();
/*

        item.setIshod(true);
        item.setNumber("+7 122 666 22 45");
        item.setTime("18:25, 7 января");
        item.setTime_call("00:45");

        for (int i = 0; i <  5 ; i++) {
            list.add(item);
        }
        item = new record();
        item.setNumber("+7 929 322 77 26");
        item.setTime("12:25, 1 января");
        item.setTime_call("00:35");

        for (int i = 0; i <  5 ; i++) {
            list.add(item);
        }
*/

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
