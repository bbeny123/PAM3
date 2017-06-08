package com.bbeny.astroweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bbeny.astroweather.model.PlaceModel;
import com.bbeny.astroweather.R;
import com.bbeny.astroweather.async.PlaceWeatherLoad;
import com.bbeny.astroweather.db.AstroDb;
import com.bbeny.astroweather.model.WeatherModel;
import com.bbeny.astroweather.util.AstroTools;

import java.util.List;

import static android.R.id.list;

/**
 * Created by bbeny on 28.05.2017.
 */

public class MenuActivity extends Activity implements View.OnClickListener {

    private static final String PREFERENCES_NAME = "astroPreferences";
    private static final String PREFERENCES_LONGITUDE = "longitudeField";
    private static final String PREFERENCES_LATITUDE = "latitudeField";
    private static final String PREFERENCES_REFRESH = "refreshField";
    private static final String GET_PLACE = "place";
    private static final String GET_WEATHER = "weather";
    private static final String CELSIUS = "YES";
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
        /*
        try {
            new PlaceWeatherLoad(getApplicationContext()).execute(GET_WEATHER, "20070464").get();
        } catch(Exception e) {
            Log.d("MAIN", "EXE");
        }
        */

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
