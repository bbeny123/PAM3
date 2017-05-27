package com.bbeny.astroweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;

public class MoonFragment extends Fragment implements IAstroFragment{

    TextView moonriseTime;
    TextView moonsetTime;
    TextView fullMoon;
    TextView newMoon;
    TextView illumination;
    TextView moonAge;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moon, container, false);
        moonriseTime = (TextView) view.findViewById(R.id.moonriseTime);
        moonsetTime = (TextView) view.findViewById(R.id.moonsetTime);
        fullMoon = (TextView) view.findViewById(R.id.fullMoon);
        newMoon = (TextView) view.findViewById(R.id.newMoon);
        illumination = (TextView) view.findViewById(R.id.illumination);
        moonAge = (TextView) view.findViewById(R.id.moonAge);
        update();
        return view;
    }

    public void update() {
        AstroCalculator.MoonInfo moonInfo = AstroTools.getAstroCalculator().getMoonInfo();
        moonriseTime.setText(AstroTools.timeFormat(moonInfo.getMoonrise()));
        moonsetTime.setText(AstroTools.timeFormat(moonInfo.getMoonset()));
        fullMoon.setText(AstroTools.dateFormat(moonInfo.getNextFullMoon()));
        newMoon.setText(AstroTools.dateFormat(moonInfo.getNextNewMoon()));
        illumination.setText(AstroTools.illuminationFormat(moonInfo.getIllumination()));
        moonAge.setText(AstroTools.azimuthFormat(moonInfo.getAge()));
    }

}