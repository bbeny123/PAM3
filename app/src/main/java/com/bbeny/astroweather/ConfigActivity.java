package com.bbeny.astroweather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * Created by bbeny on 28.05.2017.
 */

public class ConfigActivity extends Activity implements View.OnClickListener {

    private static final String PREFERENCES_NAME = "astroPreferences";
    private static final String PREFERENCES_LONGITUDE = "longitudeField";
    private static final String PREFERENCES_LATITUDE = "latitudeField";
    private static final String PREFERENCES_REFRESH = "refreshField";
    SharedPreferences config;

    EditText longitudeConfig;
    EditText latitudeConfig;
    EditText refreshConfig;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        config = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        longitudeConfig = (EditText) findViewById(R.id.longitudeConfig);
        latitudeConfig = (EditText) findViewById(R.id.latitudeConfig);
        refreshConfig = (EditText) findViewById(R.id.refreshConfig);
        okButton = (Button) findViewById(R.id.configButton);
        okButton.setOnClickListener(this);
        loadConfig();
    }

    private void loadConfig() {
        longitudeConfig.setText(config.getString(PREFERENCES_LONGITUDE, ""));
        latitudeConfig.setText(config.getString(PREFERENCES_LATITUDE, ""));
        refreshConfig.setText(config.getString(PREFERENCES_REFRESH, ""));
    }

    private void saveConfig() {
        SharedPreferences.Editor preferencesEditor = config.edit();
        String longitude = checkString(longitudeConfig.getText().toString(), -180, 180);
        String latitude = checkString(latitudeConfig.getText().toString(), -72, 73);
        String refresh = checkString(refreshConfig.getText().toString(), 1, 60);
        preferencesEditor.putString(PREFERENCES_LONGITUDE, longitude);
        preferencesEditor.putString(PREFERENCES_LATITUDE, latitude);
        preferencesEditor.putString(PREFERENCES_REFRESH, refresh);
        preferencesEditor.apply();
    }

    private String checkString(String s, int min, int max) {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(false);
        try {
            double temp = Double.parseDouble(s);
            temp = Math.min(temp, max);
            temp = Math.max(temp, min);
            return format.format(temp);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    protected void onPause() {
        saveConfig();
        super.onPause();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.configButton: {
                finish();
                break;
            }
        }
    }

}