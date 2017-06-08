package com.bbeny.astroweather.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbeny.astroweather.R;
import com.bbeny.astroweather.model.PlaceModel;
import com.bbeny.astroweather.model.WeatherModel;
import com.bbeny.astroweather.util.AstroTools;

import java.util.List;

/**
 * Created by bbeny on 28.05.2017.
 */

public class TodayFragment extends Fragment {

    private TextView place;
    private TextView date;
    private TextView currentTemp;
    private TextView dailyTemp;
    private TextView text;
    private TextView pressure;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        place = (TextView) view.findViewById(R.id.place);
        date = (TextView) view.findViewById(R.id.date);
        currentTemp = (TextView) view.findViewById(R.id.currentTemp);
        dailyTemp = (TextView) view.findViewById(R.id.dailyTemp);
        text = (TextView) view.findViewById(R.id.text);
        pressure = (TextView) view.findViewById(R.id.pressure);
        setInfo();
        return view;
    }

    private void setInfo() {
        List<WeatherModel> weatherList = AstroTools.getWeatherList(getContext());
        if(weatherList == null)
            return;
        WeatherModel weather = weatherList.get(0);
        date.setText(weather.getDate());
        currentTemp.setText(weather.getCurrentTemp());
        dailyTemp.setText(weather.getLowTemp() + " - " + weather.getHighTemp());
        text.setText(weather.getText());
        pressure.setText(weather.getPressure());
        PlaceModel placeModel = AstroTools.getCurrentPlace(getContext());
        if(placeModel != null) {
            place.setText(placeModel.getContent());
        }
    }

}
