package com.bbeny.astroweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bbeny.astroweather.db.PlaceModel;
import com.bbeny.astroweather.R;
import com.bbeny.astroweather.async.RetrieveJson;
import com.bbeny.astroweather.db.AstroDb;

import java.util.List;

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
        try {
            new RetrieveJson(getApplicationContext()).execute().get();
        } catch(Exception e) {

        }

        AstroDb astro = new AstroDb(getApplicationContext());
        astro.open();
        List<PlaceModel> a = astro.getAllPlaces();
        for(PlaceModel b: a) {
            Log.d("id", Integer.toString(b.getId()));
            Log.d("woeid", b.getWoeid());
            Log.d("content", b.getContent());
            Log.d("latitude", b.getLatitude());
            Log.d("longitude", b.getLongitude());
            Log.d("timeZone", b.getTimeZone());
        }
        astro.close();

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
