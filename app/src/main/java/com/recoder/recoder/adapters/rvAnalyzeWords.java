package com.recoder.recoder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.recoder.recoder.R;
import com.recoder.recoder.models.analyzeWords;
import com.recoder.recoder.models.recordMin;

import java.util.ArrayList;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс - адаптер, для вывода информации о
 * проанализированных словах во фрагмент
 */

public class rvAnalyzeWords extends RecyclerView.Adapter<rvAnalyzeWords.ViewHolder> {

    private ArrayList<analyzeWords> mDataset;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView word, frequency;
        public LinearLayout ll, ll_list;

        public ViewHolder(View v) {
            super(v);
            ll = (LinearLayout) v.findViewById(R.id.clickLL);
            frequency = (TextView) v.findViewById(R.id.frequency);
            word = (TextView) v.findViewById(R.id.word);
            ll_list = (LinearLayout) v.findViewById(R.id.records_list);
        }
    }

    // Конструктор
    public rvAnalyzeWords(ArrayList<analyzeWords> dataset, Context context) {
        mDataset = dataset;
        this.context = context;
    }


    @Override
    /**
     * Создает новые views
     */
    public rvAnalyzeWords.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_analyze_word_item, parent, false);

        context = parent.getContext();


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    /**
     * Заменяет контент отдельного view
     */
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.frequency.setText(mDataset.get(position).getFrequency().toString());
        holder.word.setText(mDataset.get(position).getWord());



        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Клик по записи!" , Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<recordMin> list = mDataset.get(position).getListOfRecords();

        for (int i = 0; i < list.size(); i++) {
            TextView value = new TextView(context);
            value.setTextColor(context.getResources().getColor(R.color.black));
            value.setTextSize(16);
            value.setText(list.get(i).getName() + " --> " + list.get(i).getQuantity());
            value.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            holder.ll_list.addView(value);
        }

    }


    @Override
    /**
     * Метод, для получения количества записей в списке
     */
    public int getItemCount() {
        return mDataset.size();
    }




}