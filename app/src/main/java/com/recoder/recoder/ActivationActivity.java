package com.recoder.recoder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.recoder.recoder.Helper.PrefsHelper;

public class ActivationActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView textView;
    public String PREF_API_KEY = "PrefApiKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Память");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textView = (TextView) findViewById(R.id.ApiKey);
        textView.setText(PrefsHelper.readPrefString(App.getContext(), PREF_API_KEY));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
