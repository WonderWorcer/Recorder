package com.recoder.recoder.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.R;
import com.recoder.recoder.models.words;

import java.util.ArrayList;

/**
 * Created by WonderWorcer on 29.04.2017.
 */

public class rvWords extends RecyclerView.Adapter<rvWords.ViewHolder> {

    private ArrayList<words> mDataset;
    private Context context;
    private String _id;
    DBHelper dbHelper = new DBHelper(App.getContext());
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView word, priority;
        public LinearLayout ll;
        public ImageButton ib;

        public ViewHolder(View v) {
            super(v);
            ll = (LinearLayout) v.findViewById(R.id.clickLL);
            priority = (TextView) v.findViewById(R.id.priority);
            word = (TextView) v.findViewById(R.id.word);
            ib = (ImageButton) v.findViewById(R.id.iv_dop);
        }
    }

    // Конструктор
    public rvWords(ArrayList<words> dataset, Context context) {
        mDataset = dataset;
        this.context = context;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public rvWords.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_word_item, parent, false);

        context = parent.getContext();

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.priority.setText(mDataset.get(position).getPriority().toString());
        holder.word.setText(mDataset.get(position).getWord());


        holder.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, position);
            }
        });

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Клик по записи!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showPopup(View view, final int position) {
        // pass the imageview id

        final PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.rv_rec_item_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit: {

                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.add_dialog, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);
                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        final EditText inputWord = (EditText) promptsView
                                .findViewById(R.id.addword);
                        final EditText inputPrioritet = (EditText) promptsView
                                .findViewById(R.id.addprioritet);
                        Cursor cursor = db.query(dbHelper.TABLE_USER_DICTIONARY_WORDS,
                                new String[]{dbHelper.KEY_WORD, dbHelper.VALUE_WORD},
                                "_id = ?", new String[]{mDataset.get(position).get_id()}, null, null, null, null);
                        if (cursor.moveToFirst()) {
                            int keyWordColIndex = cursor.getColumnIndex(dbHelper.KEY_WORD);
                            int valueWordColIndex = cursor.getColumnIndex(dbHelper.VALUE_WORD);
                            int idColIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
                            do {
                                inputWord.setText(cursor.getString(keyWordColIndex));
                                inputPrioritet.setText(Integer.toString(cursor.getInt(valueWordColIndex)));
                                _id = mDataset.get(position).get_id();
                            } while (cursor.moveToNext());
                        }
                        cursor.close();

                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // get user input and set it to result
                                                // edit text
                                                ContentValues contentValues = new ContentValues();
                                                contentValues.put(dbHelper.KEY_WORD, inputWord.getText().toString());
                                                if (Integer.parseInt(inputPrioritet.getText().toString()) > 10)
                                                    contentValues.put(dbHelper.VALUE_WORD, 10);
                                                else
                                                    contentValues.put(dbHelper.VALUE_WORD, Integer.parseInt(inputPrioritet.getText().toString()));

                                                db.update(dbHelper.TABLE_USER_DICTIONARY_WORDS, contentValues, "_id = ?", new String[]{_id});

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




                        Toast.makeText(context, "Изменить элемент " + Integer.toString(position) , Toast.LENGTH_SHORT).show();
                    }
                        break;
                    case R.id.delete: {
                        db.delete(dbHelper.TABLE_USER_DICTIONARY_WORDS, "_id = " + mDataset.get(position).get_id(), null);
                        Toast.makeText(context, "Запись удалена", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                        return false;
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }




}