package com.bbeny.astroweather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Beny on 27.05.2017.
 */

class AstroTools {

    private static final String PREFERENCES_NAME = "astroPreferences";
    private static final String PREFERENCES_LONGITUDE = "longitudeField";
    private static final String PREFERENCES_LATITUDE = "latitudeField";
    private static final String PREFERENCES_REFRESH = "refreshField";

    static int getRefreshRate(final Context context) {
        SharedPreferences config = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        return Integer.parseInt(config.getString(PREFERENCES_REFRESH, "15")) * 60000;
    }

    static String getLatitude(final Context context) {
        SharedPreferences config = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        return config.getString(PREFERENCES_LATITUDE, "");
    }

    static String getLongitude(final Context context) {
        SharedPreferences config = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        return config.getString(PREFERENCES_LONGITUDE, "");
    }

    static AstroCalculator getAstroCalculator(final Context context) {
        AstroDateTime astroDateTime = getAstroDateTime();
        AstroCalculator.Location astroLocation = getAstroLocation(context);
        return new AstroCalculator(astroDateTime, astroLocation);
    }

    private static AstroCalculator.Location getAstroLocation(final Context context) {
        SharedPreferences config = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        double longitude = Double.parseDouble(config.getString(PREFERENCES_LONGITUDE, ""));
        double latitude = Double.parseDouble(config.getString(PREFERENCES_LATITUDE, ""));
        return new AstroCalculator.Location(latitude,longitude);
    }

    private static AstroDateTime getAstroDateTime() {
        GregorianCalendar cal = new GregorianCalendar();
        int year = cal.get(GregorianCalendar.YEAR);
        int month = cal.get(GregorianCalendar.MONTH);
        int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
        int minute = cal.get(GregorianCalendar.MINUTE);
        int second = cal.get(GregorianCalendar.SECOND);
        int timeZone = cal.get(GregorianCalendar.ZONE_OFFSET) / 3600000;
        boolean daylightSaving = cal.getTimeZone().inDaylightTime(cal.getTime());
        return new AstroDateTime(year, month, day, hour, minute, second, timeZone, daylightSaving);
    }

    static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new GregorianCalendar().getTime());
    }

    static String timeFormat(AstroDateTime dateTime) {
        GregorianCalendar cal = astroDateTimeToGregorianCalendar(dateTime);
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(cal.getTime());
    }

    static String dateFormat(AstroDateTime dateTime) {
        GregorianCalendar cal = astroDateTimeToGregorianCalendar(dateTime);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(cal.getTime());
    }

    static String azimuthFormat(double azimuth) {
        if (!Double.isNaN(azimuth))
            return String.format(Locale.getDefault(), "%.2f", azimuth);
        else
            return "AstroLib Error";
    }

    static String illuminationFormat(double illumination) {
        return String.format(Locale.getDefault(), "%.2f%%", illumination * 100);
    }

    private static GregorianCalendar astroDateTimeToGregorianCalendar(AstroDateTime dateTime) {
        int year = dateTime.getYear();
        int month = dateTime.getMonth();
        int day = dateTime.getDay();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        int second = dateTime.getSecond();
        return new GregorianCalendar(year, month, day, hour, minute, second);
    }
}
