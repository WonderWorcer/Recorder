package com.recoder.recoder;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.recoder.recoder.Helper.DBHelper;

public class MemoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    DBHelper dbHelper = new DBHelper(App.getContext());
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Память");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        findViewById(R.id.recordDirectory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MemoryActivity.this, "Нажал на кнопку директория", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.deleteNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(dbHelper.TABLE_RECORDS, null, null);
                Toast.makeText(MemoryActivity.this, "Записи успешно удалены", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
