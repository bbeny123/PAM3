package com.bbeny.astroweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by bbeny on 28.05.2017.
 */

public class MenuActivity extends Activity implements View.OnClickListener {

    private static final String PREFERENCES_NAME = "astroPreferences";
    private static final String PREFERENCES_LONGITUDE = "longitudeField";
    private static final String PREFERENCES_LATITUDE = "latitudeField";
    private static final String PREFERENCES_REFRESH = "refreshField";
    private SharedPreferences config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        config = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        Button astroCalcButton = (Button) findViewById(R.id.astroCalcButton);
        astroCalcButton.setOnClickListener(this);
        Button configButton = (Button) findViewById(R.id.configButton);
        configButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.astroCalcButton: {
                if(checkConfig()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    break;
                }
            }
            case R.id.configButton: {
                Intent intent = new Intent(this, ConfigActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private boolean checkConfig() {
        return config.getString(PREFERENCES_LONGITUDE, "").length() > 0 && config.getString(PREFERENCES_LATITUDE, "").length() > 0 && config.getString(PREFERENCES_REFRESH, "").length() > 0;
    }
}
