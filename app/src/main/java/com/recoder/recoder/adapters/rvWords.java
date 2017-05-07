package com.recoder.recoder.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.recoder.recoder.R;
import com.recoder.recoder.models.words;

import java.util.ArrayList;

/**
 * Created by WonderWorcer on 29.04.2017.
 */

public class rvWords extends RecyclerView.Adapter<rvWords.ViewHolder> {

    private ArrayList<words> mDataset;
    private Context context;


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
                    case R.id.edit:
                        Toast.makeText(context, "Изменить элемент " + Integer.toString(position) , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.delete:
                        Toast.makeText(context, "Удалить элемент " + Integer.toString(position) , Toast.LENGTH_SHORT).show();
                        break;
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