package com.venky.core.date;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Time extends Date {

    /**
     * 
     */
    private static final long serialVersionUID = -8327376604968255069L;
    private transient final Calendar calendar;

    public Time(final String time) {
        super(DateUtils.getTime(time).getTime());
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getTime());
    }

    @Override
    public int getHours() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    @Override
    public int getMinutes() {
        return calendar.get(Calendar.MINUTE);
    }

    @Override
    public int getSeconds() {
        return calendar.get(Calendar.SECOND);
    }

    @Override
    public String toString() {
        return toString(TimeZone.getDefault());
    }

    public String toString(final String tzId) {
        return toString(TimeZone.getTimeZone(tzId));
    }

    public String toString(final TimeZone tz) {
        return DateUtils.getTimeStr(this, tz);
    }
}
