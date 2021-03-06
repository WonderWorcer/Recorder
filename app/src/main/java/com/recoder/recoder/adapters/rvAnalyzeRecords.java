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
import com.recoder.recoder.models.words;

import java.util.ArrayList;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс - адаптер, для вывода информации о
 * проанализированных записях во фрагмент
 */
public class rvAnalyzeRecords extends RecyclerView.Adapter<rvAnalyzeRecords.ViewHolder> {

    private ArrayList<analyzeRecords> mDataset;///<Список объектов для анализа записей
    private Context context;///<Контекст приложения


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


    @Override
    /**
     * Создает новые views
     */
    public rvAnalyzeRecords.ViewHolder onCreateViewHolder(ViewGroup parent,
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


        holder.word.setText(mDataset.get(position).getName());


        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Клик по записи!", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<words> list = mDataset.get(position).getListOfWords();

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++)
                if (list.get(i).getWord().equals(list.get(j).getWord())) {
                    list.remove(j);
                    j--;
                }
        }
        int frequency = 0;
        for (int i = 0; i < list.size(); i++)
            frequency += list.get(i).getPriority();
        mDataset.get(position).setPriority(frequency);
        if (mDataset.get(position).getPriority() < 5)
            holder.frequency.setTextColor(context.getResources().getColor(R.color.lime));
        else if (mDataset.get(position).getPriority() > 4 && mDataset.get(position).getPriority() < 10)
            holder.frequency.setTextColor(context.getResources().getColor(R.color.yellow));
        else
            holder.frequency.setTextColor(context.getResources().getColor(R.color.red));
        holder.frequency.setText(mDataset.get(position).getPriority().toString());

        for (int i = 0; i < list.size(); i++) {
            TextView value = new TextView(context);

            if (list.get(i).getFilterName().equals("dragWords"))
                value.setTextColor(context.getResources().getColor(R.color.gold));
            else if (list.get(i).getFilterName().equals("extremistWords"))
                value.setTextColor(context.getResources().getColor(R.color.red));
            else if (list.get(i).getFilterName().equals("theftWords"))
                value.setTextColor(context.getResources().getColor(R.color.aqua));
            else if (list.get(i).getFilterName().equals("profanityWords"))
                value.setTextColor(context.getResources().getColor(R.color.lime));
            else if (list.get(i).getFilterName().equals("stateSecretWords"))
                value.setTextColor(context.getResources().getColor(R.color.pink));
            else if (list.get(i).getFilterName().equals("bankSecretWords"))
                value.setTextColor(context.getResources().getColor(R.color.purple));
            else if (list.get(i).getFilterName().equals("userDictionaryWords"))
                value.setTextColor(context.getResources().getColor(R.color.yellow));

            value.setTextSize(16);
            value.setText(list.get(i).getWord() + " --> " + list.get(i).getPriority().toString());
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