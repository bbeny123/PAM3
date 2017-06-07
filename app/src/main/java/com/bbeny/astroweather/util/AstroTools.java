package com.bbeny.astroweather.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Beny on 27.05.2017.
 */

public class AstroTools {

    private static final String PREFERENCES_NAME = "astroPreferences";
    private static final String PREFERENCES_LONGITUDE = "longitudeField";
    private static final String PREFERENCES_LATITUDE = "latitudeField";
    private static final String PREFERENCES_REFRESH = "refreshField";

    private static void test2(String result) {
        try {
            JSONObject reader = new JSONObject(result);
            JSONObject sys = reader.getJSONObject("query");
            String temp = sys.getString("count");
            Log.d("count", temp);
        } catch (Exception e) {
            Log.d("Exception", "Excep");
        }
    }

    public static void test() {
        String address = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.places(1)%20where%20text=%22dmosin%22&format=json";
        InputStream in = null;
        HttpURLConnection urlConnection = null;
        try {
            System.out.println("ADDRESS: " + address);
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED)
                in = new BufferedInputStream(urlConnection.getInputStream());
            String resultString = readStream(in);
            System.out.println(resultString);
            test2(resultString);
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
    }

    private static String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;) sb.append((char) c);
        return sb.toString();
    }

    public static int getRefreshRate(final Context context) {
        SharedPreferences config = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        return Integer.parseInt(config.getString(PREFERENCES_REFRESH, "15")) * 60000;
    }

    public static String getLatitude(final Context context) {
        SharedPreferences config = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        return config.getString(PREFERENCES_LATITUDE, "");
    }

    public static String getLongitude(final Context context) {
        SharedPreferences config = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        return config.getString(PREFERENCES_LONGITUDE, "");
    }

    public static AstroCalculator getAstroCalculator(final Context context) {
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

    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new GregorianCalendar().getTime());
    }

    public static String timeFormat(AstroDateTime dateTime) {
        GregorianCalendar cal = astroDateTimeToGregorianCalendar(dateTime);
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(cal.getTime());
    }

    public static String dateFormat(AstroDateTime dateTime) {
        GregorianCalendar cal = astroDateTimeToGregorianCalendar(dateTime);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(cal.getTime());
    }

    public static String azimuthFormat(double azimuth) {
        if (!Double.isNaN(azimuth))
            return String.format(Locale.getDefault(), "%.2f", azimuth);
        else
            return "AstroLib Error";
    }

    public static String illuminationFormat(double illumination) {
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
