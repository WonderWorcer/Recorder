package com.recoder.recoder.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.recoder.recoder.App;
import com.recoder.recoder.Facade.ReadFromDB;
import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.Helper.PrefsHelper;
import com.recoder.recoder.MainActivity;
import com.recoder.recoder.R;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс необходимый для иницилизации фрагмента
 */
public class fragmentMainPage extends Fragment {
    Toolbar toolbar;
    TextView allFiles, analizedFiles, userFilter,
            stateSecretFilter, bankSecretFilter,
            extremizmFilter, dragFilter, theftFilter,
            profanityFilter;
    Switch onOffRecord;
    public fragmentMainPage() {

    }


    @Override
    /**
     * Инициализация фрагмента
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.fragment_main_page, container, false);
        ;
        allFiles = (TextView) v.findViewById(R.id.allFiles);
        analizedFiles = (TextView) v.findViewById(R.id.analizedFiles);
        userFilter = (TextView) v.findViewById(R.id.userFilter);
        stateSecretFilter = (TextView) v.findViewById(R.id.stateSecretFilter);
        bankSecretFilter = (TextView) v.findViewById(R.id.bankSecretFilter);
        extremizmFilter = (TextView) v.findViewById(R.id.extremizmFiltr);
        dragFilter = (TextView) v.findViewById(R.id.dragFilter);
        theftFilter = (TextView) v.findViewById(R.id.theftFilter);
        profanityFilter = (TextView) v.findViewById(R.id.profanityFilter);
        onOffRecord = (Switch) v.findViewById(R.id.onOffRecord);

        ReadFromDB readFromDB = new ReadFromDB();
        readFromDB.generateMainPage();

        allFiles.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), App.PREF_ALL_FILES)));
        analizedFiles.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), App.PREF_ANALIZED_FILES)));
        userFilter.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), DBHelper.USER_DICTIONARY_FILTER)));
        stateSecretFilter.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), DBHelper.STATE_SECRET_FILTER)));
        bankSecretFilter.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), DBHelper.BANK_SECRET_FILTER)));
        extremizmFilter.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), DBHelper.EXTREMIST_FILTER)));
        dragFilter.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), DBHelper.DRAG_FILTER)));
        theftFilter.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), DBHelper.THEFT_FILTER)));
        profanityFilter.setText(Integer.toString(PrefsHelper.readPrefInt(App.getContext(), DBHelper.PROFANITY_FILTER)));


        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Главная");

        Drawable mDrawable = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.ic_dehaze_24dp);
        toolbar.setNavigationIcon(mDrawable);
        if (PrefsHelper.readPrefBool(App.getContext(), App.PREF_ISRECORDING)) {
            onOffRecord.setChecked(true);
        } else
            onOffRecord.setChecked(false);

        toolbar.inflateMenu(R.menu.drawer_view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        onOffRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOffRecord.isChecked()) {
                    onOffRecord.setText("Запись включена");
                    PrefsHelper.writePrefBool(App.getContext(), App.PREF_ISRECORDING, true);
                    App.getCallRecord().startCallReceiver();
                } else {
                    PrefsHelper.writePrefBool(App.getContext(), App.PREF_ISRECORDING, false);
                    App.getCallRecord().stopCallReceiver();
                    onOffRecord.setText("Запись выключена");
                }
            }

        });

        return v;
    }

}
