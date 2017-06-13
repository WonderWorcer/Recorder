package com.recoder.recoder.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recoder.recoder.MainActivity;
import com.recoder.recoder.R;
import com.recoder.recoder.adapters.rvAnalyzeWords;
import com.recoder.recoder.models.analyzeWords;
import com.recoder.recoder.models.recordMin;

import java.util.ArrayList;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс необходимый для иницилизации фрагмента
 */
public class fragmentAnalyzeWords extends Fragment {

    Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<analyzeWords> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_analyze_words, container, false);

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Анализ по словам");

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


        analyzeWords item = new analyzeWords();
        item.setWord("Слово");
        item.setFrequency(5);

        recordMin mm = new recordMin();
        mm.setName("Запись 1");
        mm.setQuantity(2);

        ArrayList<recordMin> liist = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            liist.add(mm);
        }

        item.setListOfRecords(liist);

        for (int i = 0; i <  5 ; i++) {
            list.add(item);
        }

        try {
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new rvAnalyzeWords(list, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

}
