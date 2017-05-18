package com.recoder.recoder.fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.recoder.recoder.ActivationActivity;
import com.recoder.recoder.App;
import com.recoder.recoder.CallRecord;
import com.recoder.recoder.Helper.PrefsHelper;
import com.recoder.recoder.MainActivity;
import com.recoder.recoder.MemoryActivity;
import com.recoder.recoder.PassActivity;
import com.recoder.recoder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentSettings extends Fragment {

    Toolbar toolbar;
    LinearLayout ll_mem;
    LinearLayout ll_pass;
    LinearLayout ll_default;
    LinearLayout ll_activition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_settings, container, false);

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ll_default = (LinearLayout) v.findViewById(R.id.ll_default);
        ll_mem = (LinearLayout) v.findViewById(R.id.ll_mem);
        ll_pass = (LinearLayout) v.findViewById(R.id.ll_pass);
        ll_activition = (LinearLayout) v.findViewById(R.id.ll_activation);

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Настройки");

        Drawable mDrawable = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.ic_dehaze_24dp);
        toolbar.setNavigationIcon(mDrawable);

        toolbar.inflateMenu(R.menu.drawer_view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });


        ll_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrefsHelper.writePrefBool(App.getContext(), App.PREF_DELETE_AFTER_10_ATTEMPT, false);
                PrefsHelper.writePrefBool(App.getContext(), App.PREF_PASSWORD_ACTIVE, false);
                PrefsHelper.writePrefBool(App.getContext(), App.PREF_CHANGE_PASSWORD, false);
                PrefsHelper.writePrefInt(App.getContext(), App.PREF_AUTO_DELETE, 0);
                PrefsHelper.writePrefString(App.getContext(), App.PREF_DIR_PATH, Environment.getExternalStorageDirectory().getPath());
                PrefsHelper.writePrefString(App.getContext(), CallRecord.PREF_DIR_NAME, App.PREF_DEFAULT_FOLDER);
                PrefsHelper.writePrefBool(App.getContext(), App.PREF_ISRECORDING, true);
                PrefsHelper.writePrefString(App.getContext(), App.PREF_PASSWORD, null);
                PrefsHelper.writePrefString(App.getContext(), App.PREF_API_KEY, "AIzaSyCvfglaj2kcmjzNY5kyItkBx5wsHXQm8Y4");
                Toast.makeText(getActivity(), "Настройки по умолчанию пременены", Toast.LENGTH_SHORT).show();
            }
        });

        ll_mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MemoryActivity.class);
                startActivity(intent);
            }
        });


        ll_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PassActivity.class);
                startActivity(intent);
            }
        });

        ll_activition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivationActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

}
