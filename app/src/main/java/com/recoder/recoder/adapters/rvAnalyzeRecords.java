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
import com.recoder.recoder.models.analyzeRecords;
import com.recoder.recoder.models.analyzeWords;
import com.recoder.recoder.models.recordMin;
import com.recoder.recoder.models.words;

import java.util.ArrayList;

/**
 * Created by WonderWorcer on 29.04.2017.
 */

public class rvAnalyzeRecords extends RecyclerView.Adapter<rvAnalyzeRecords.ViewHolder> {

    private ArrayList<analyzeRecords> mDataset;
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
    public rvAnalyzeRecords(ArrayList<analyzeRecords> dataset, Context context) {
        mDataset = dataset;
        this.context = context;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public rvAnalyzeRecords.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_analyze_word_item, parent, false);

        context = parent.getContext();

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.frequency.setText(mDataset.get(position).getPriority().toString());
        holder.word.setText(mDataset.get(position).getName());


        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Клик по записи!" , Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<words> list = mDataset.get(position).getListOfWords();

        for (int i = 0; i < list.size(); i++) {
            TextView value = new TextView(context);
            if (list.get(i).getPriority() > 10 && list.get(i).getPriority() < 15) {
                value.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                value.setTextColor(context.getResources().getColor(R.color.ItemTint));
            }
            value.setTextSize(16);
            value.setText(list.get(i).getWord() + " --> " + list.get(i).getPriority().toString());
            value.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            holder.ll_list.addView(value);
        }

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }




}