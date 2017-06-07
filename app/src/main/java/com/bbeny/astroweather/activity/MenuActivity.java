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
        int result = -1;
        try {
            result = new PlaceWeatherLoad(getApplicationContext()).execute(GET_PLACE, "miasto").get();
        } catch(Exception e) {
            Log.d("MAIN", "EXE");
        }
        String woeid = "1";
        Log.d("result", Integer.toString(result));
        AstroDb astro = new AstroDb(getApplicationContext());
        astro.open();
        List<PlaceModel> a = astro.getAllPlaces();
        for(PlaceModel b: a) {
            Log.d("id", Integer.toString(b.getId()));
            Log.d("woeid", b.getWoeid());
            woeid = b.getWoeid();
            Log.d("content", b.getContent());
            Log.d("latitude", b.getLatitude());
            Log.d("longitude", b.getLongitude());
            Log.d("timeZone", b.getTimeZone());
        }
        astro.close();
        try {
            result = new PlaceWeatherLoad(getApplicationContext()).execute(GET_WEATHER, woeid, CELSIUS).get();
        } catch(Exception e) {
            Log.d("MAIN", "EXE");
        }
        Log.d("result", Integer.toString(result));
        List<WeatherModel> ab = AstroTools.getWeatherList(getApplicationContext());
        for(WeatherModel b: ab) {
            Log.d("date", b.getDate());
            Log.d("highTemp", b.getHighTemp());
            Log.d("lowTemp", b.getLowTemp());
            Log.d("text", b.getText());
        }

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
