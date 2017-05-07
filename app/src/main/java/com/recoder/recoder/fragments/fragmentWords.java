package com.recoder.recoder.fragments;


import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.recoder.recoder.App;
import com.recoder.recoder.MainActivity;
import com.recoder.recoder.R;
import com.recoder.recoder.adapters.rvWords;
import com.recoder.recoder.models.words;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentWords extends Fragment {

    Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<words> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_words, container, false);

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Моя библиотека");

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

        v.findViewById(R.id.clickAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Добавление слова", Toast.LENGTH_SHORT).show();

                LayoutInflater li = LayoutInflater.from(App.getContext());
                View promptsView = li.inflate(R.layout.add_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        App.getContext());
// set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText inputWord = (EditText) promptsView
                        .findViewById(R.id.addword);
                final EditText inputPrioritet = (EditText) promptsView
                        .findViewById(R.id.addprioritet);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        words item = new words();
                                        item.setWord(inputWord.getText().toString());
                                        item.setPriority(Integer.parseInt(inputPrioritet.getText().toString()));
                                        list.add(item);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();



            }
        });

        words item = new words();
        item.setWord("Слово");
        item.setPriority(150);

//        for (int i = 0; i <  5 ; i++) {
//            list.add(item);
//        }

        try {
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new rvWords(list, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

}
