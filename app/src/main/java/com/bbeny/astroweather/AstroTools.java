package com.bbeny.astroweather;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by bbeny on 27.05.2017.
 */

public class AstroTools {

    public static AstroCalculator getAstroCalculator() {
        AstroDateTime astroDateTime = getAstroDateTime();
        AstroCalculator.Location astroLocation = new AstroCalculator.Location(10,10);
        return new AstroCalculator(astroDateTime, astroLocation);
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

    public static String timeFormat(AstroDateTime dateTime) {
        GregorianCalendar cal = astroDateTimeToGregorianCalendar(dateTime);
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(cal.getTime());
    }

    public static String dateFormat(AstroDateTime dateTime) {
        GregorianCalendar cal = astroDateTimeToGregorianCalendar(dateTime);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(cal.getTime());
    }

    public static String azimuthFormat(double azimuth) {
        return String.format(Locale.getDefault(), "%.2f", azimuth);
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
