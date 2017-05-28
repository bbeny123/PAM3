package com.bbeny.astroweather;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bbeny on 28.05.2017.
 */

public class TimeFragment extends Fragment {

    TextView time;
    TextView latitude;
    TextView longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        time = (TextView) view.findViewById(R.id.time);
        latitude = (TextView) view.findViewById(R.id.latitude);
        longitude = (TextView) view.findViewById(R.id.textview16);
        setInfo();
        update();
        return view;
    }

    private void update() {
        final Handler h = new Handler();
        final int delay = 1000;
        h.postDelayed(new Runnable(){
            public void run(){
                time.setText(AstroTools.getCurrentTime());
                h.postDelayed(this, delay);
            }
        }, delay);
    }

    private void setInfo() {
        time.setText(AstroTools.getCurrentTime());
        latitude.setText(AstroTools.getLatitude(getContext()));
        longitude.setText(AstroTools.getLongitude(getContext()));
    }

}
