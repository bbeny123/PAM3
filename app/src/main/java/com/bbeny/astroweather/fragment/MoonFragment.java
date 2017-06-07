package com.bbeny.astroweather.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.bbeny.astroweather.util.AstroTools;
import com.bbeny.astroweather.R;

public class MoonFragment extends Fragment {

    private TextView moonriseTime;
    private TextView moonsetTime;
    private TextView fullMoon;
    private TextView newMoon;
    private TextView illumination;
    private TextView moonAge;
    private AstroCalculator.MoonInfo moonInfo;
    private Handler h;
    private int delay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moon, container, false);
        moonriseTime = (TextView) view.findViewById(R.id.moonriseTime);
        moonsetTime = (TextView) view.findViewById(R.id.moonsetTime);
        fullMoon = (TextView) view.findViewById(R.id.fullMoon);
        newMoon = (TextView) view.findViewById(R.id.newMoon);
        illumination = (TextView) view.findViewById(R.id.illumination);
        moonAge = (TextView) view.findViewById(R.id.moonAge);
        moonInfo = AstroTools.getAstroCalculator(getContext()).getMoonInfo();
        h = new Handler();
        delay = AstroTools.getRefreshRate(getContext());
        setInfo();
        update();
        return view;
    }

    private void setInfo() {
        if (moonInfo.getMoonrise() != null)
            moonriseTime.setText(AstroTools.timeFormat(moonInfo.getMoonrise()));
        else
            moonriseTime.setText("AstroLib Error");
        if (moonInfo.getMoonset() != null)
            moonsetTime.setText(AstroTools.timeFormat(moonInfo.getMoonset()));
        else
            moonsetTime.setText("AstroLib Error");
        fullMoon.setText(AstroTools.dateFormat(moonInfo.getNextFullMoon()));
        newMoon.setText(AstroTools.dateFormat(moonInfo.getNextNewMoon()));
        illumination.setText(AstroTools.illuminationFormat(moonInfo.getIllumination()));
        moonAge.setText(AstroTools.azimuthFormat(moonInfo.getAge() / 7));
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