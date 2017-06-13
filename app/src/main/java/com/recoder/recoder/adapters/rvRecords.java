package com.recoder.recoder.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.DBHelper;
import com.recoder.recoder.R;
import com.recoder.recoder.Tools.FileWorker;
import com.recoder.recoder.models.record;

import java.io.File;
import java.util.ArrayList;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс - адаптер, для вывода информации о
 * записях во фрагмент
 */

public class rvRecords extends RecyclerView.Adapter<rvRecords.ViewHolder> {

    private ArrayList<record> mDataset;
    private Context context;
    DBHelper dbHelper = new DBHelper(App.getContext());
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView number, time, time_call;
        public ImageView vhod, ishod;
        public LinearLayout ll;
        public ImageButton ib;
        public de.hdodenhof.circleimageview.CircleImageView profile_image;

        public ViewHolder(View v) {
            super(v);
            ll = (LinearLayout) v.findViewById(R.id.clickLL);
            number = (TextView) v.findViewById(R.id.number);
            time = (TextView) v.findViewById(R.id.time);
            time_call = (TextView) v.findViewById(R.id.time_call);
            vhod = (ImageView) v.findViewById(R.id.vhod);
            ishod = (ImageView) v.findViewById(R.id.ishod);
            ib = (ImageButton) v.findViewById(R.id.iv_dop);
            profile_image = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.profile_image);
        }
    }

    // Конструктор
    public rvRecords(ArrayList<record> dataset, Context context) {
        mDataset = dataset;
        this.context = context;
    }


    @Override
    /**
     * Создает новые views
     */
    public rvRecords.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_record_item, parent, false);

        context = parent.getContext();



        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    /**
     * Заменяет контент отдельного view
     */
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.number.setText(mDataset.get(position).getNumber());
        holder.time.setText(mDataset.get(position).getTime());
        holder.time_call.setText(mDataset.get(position).getTime_call());

        if (mDataset.get(position).ishod()) {
            holder.vhod.setVisibility(View.GONE);
        } else {
            holder.ishod.setVisibility(View.GONE);
        }

        holder.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, position);
            }
        });

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                Cursor cursor = db.query(dbHelper.TABLE_RECORDS, new String[]{dbHelper.RECORD_PATH},
                        "_id = ?", new String[]{Integer.toString(mDataset.get(position).get_id())}, null, null, null, null);
                if (cursor.moveToFirst()) {
                    int pathColIndex = cursor.getColumnIndex(dbHelper.RECORD_PATH);
                    File file = new File(cursor.getString(pathColIndex));
                    intent.setDataAndType(Uri.fromFile(file), "audio/*");
                    App.getContext().startActivity(intent);
                }

            }
        });

    }

    /**
     * Дополнительное меню для каждой записи
     *
     * @param view     - объект, к которому привязано меню
     * @param position - позиция объекта меню
     */
    private void showPopup(View view, final int position) {

        final PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.rv_rec_item_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        Toast.makeText(context, "Изменить элемент " + Integer.toString(position), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.delete: {
                        FileWorker fileWorker = new FileWorker();
                        fileWorker.deleteSelectedFile(mDataset.get(position).getPath());
                        db.delete(dbHelper.TABLE_USED_WORDS, dbHelper.WORDS_ON_RECORD + " = " + mDataset.get(position).get_id(), null);
                        db.delete(dbHelper.TABLE_RECORDS, "_id = " + mDataset.get(position).get_id(), null);
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
    /**
     * Метод, для получения количества записей в списке
     */
    public int getItemCount() {
        return mDataset.size();
    }


}