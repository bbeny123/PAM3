package com.bbeny.astroweather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    private static final String PREFERENCES_PLACE = "place";
    private static final String PREFERENCES_UNIT = "unit";
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
        if(!isOnline()) {
            Toast toast = Toast.makeText(getApplicationContext(), "INTERNET", Toast.LENGTH_SHORT);
            toast.show();
        }
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private boolean checkConfig() {
        return config.getString(PREFERENCES_PLACE, "").length() > 0 && config.getInt(PREFERENCES_UNIT, -1) > 0;
    }
}
