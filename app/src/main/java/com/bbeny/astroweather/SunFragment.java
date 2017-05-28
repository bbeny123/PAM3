package com.bbeny.astroweather;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;

public class SunFragment extends Fragment {

    TextView sunriseAzimuth;
    TextView sunriseTime;
    TextView sunsetAzimuth;
    TextView sunsetTime;
    TextView morningTime;
    TextView eveningTime;
    AstroCalculator.SunInfo sunInfo;
    Handler h;
    int delay;

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
        return view;
    }

    public void setInfo() {
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