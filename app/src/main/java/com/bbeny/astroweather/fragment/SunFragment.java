package com.bbeny.astroweather.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.bbeny.astroweather.util.AstroTools;
import com.bbeny.astroweather.R;

public class SunFragment extends Fragment {

    private TextView sunriseAzimuth;
    private TextView sunriseTime;
    private TextView sunsetAzimuth;
    private TextView sunsetTime;
    private TextView morningTime;
    private TextView eveningTime;
    private AstroCalculator.SunInfo sunInfo;
    private Handler h;
    private int delay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sun, container, false);
        sunriseAzimuth = (TextView) view.findViewById(R.id.sunriseAzimuth);
        sunriseTime = (TextView) view.findViewById(R.id.sunriseTime);
        sunsetAzimuth = (TextView) view.findViewById(R.id.sunsetAzimuth);
        sunsetTime = (TextView) view.findViewById(R.id.sunsetTime);
        morningTime = (TextView) view.findViewById(R.id.morningTime);
        eveningTime = (TextView) view.findViewById(R.id.eveningTime);
        sunInfo = AstroTools.getAstroCalculator(getContext()).getSunInfo();
        h = new Handler();
        delay = AstroTools.getRefreshRate(getContext());
        setInfo();
        update();
        Log.d("CHUJ", "CHUJ");
        return view;
    }

    private void setInfo() {
        sunriseAzimuth.setText(AstroTools.azimuthFormat(sunInfo.getAzimuthRise()));
        sunriseTime.setText(AstroTools.timeFormat(sunInfo.getSunrise()));
        sunsetAzimuth.setText(AstroTools.azimuthFormat(sunInfo.getAzimuthSet()));
        sunsetTime.setText(AstroTools.timeFormat(sunInfo.getSunset()));
        morningTime.setText(AstroTools.timeFormat(sunInfo.getTwilightMorning()));
        eveningTime.setText(AstroTools.timeFormat(sunInfo.getTwilightEvening()));
    }

    private void update() {
        h.postDelayed(new Runnable(){
            public void run(){
                setInfo();
                h.postDelayed(this, delay);
            }
        }, delay);
    }

}