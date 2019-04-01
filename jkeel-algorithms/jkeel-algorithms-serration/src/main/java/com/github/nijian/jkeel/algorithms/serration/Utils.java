package com.github.nijian.jkeel.algorithms.serration;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Calendar;
import java.util.Date;

/**
 * Utils
 *
 * @author nj
 * @since 0.0.1
 */
public final class Utils {

    public final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * is nearest age at date
     * Note: if date = nearest passed birthday + 6 month then it will be round up to nearest age.
     * For example: date = 28/02/2018
     * birthdate = 30/08/1989
     * Then Nearest Passed Birthday 30/08/2017
     * 6 months after nearest passed birthday is: 28/02/2018  which is also date
     * => Final age is: 2017 – 1989 + 1  = 29 years old
     *
     * @param date date
     * @param birthdate birthday
     * @return nearest age
     */
    public static int nearestAge(Date date, Date birthdate) {
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        int y1 = a.get(Calendar.YEAR);
        int m1 = a.get(Calendar.MONTH);
        int d1 = a.get(Calendar.DAY_OF_MONTH);

        Calendar b = Calendar.getInstance();
        b.setTime(birthdate);
        int y2 = b.get(Calendar.YEAR);
        int m2 = b.get(Calendar.MONTH);
        int d2 = b.get(Calendar.DAY_OF_MONTH);

        Calendar c = Calendar.getInstance();
        int y3;
        if (m2 < m1 || (m2 == m1 && d2 < d1)) {
            y3 = y1;
            c.set(y3, m2, d2);
        } else {
            y3 = y1 - 1;
            c.set(y3, m2, d2);
        }
        c.add(Calendar.MONTH, 6);
        if (c.before(a) || c.equals(a)) {
            return y3 - y2 + 1;
        } else {
            return y3 - y2;
        }
    }

    public static int actualAge(boolean isBeginOfMonth, Date date, Date birthdate, int monthIndex) {
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        if (isBeginOfMonth) {
            a.add(Calendar.MONTH, monthIndex);
        } else {
            a.add(Calendar.MONTH, monthIndex + 1);
            a.add(Calendar.DAY_OF_MONTH, -1);
        }
        int aYear = a.get(Calendar.YEAR);

        Calendar b = Calendar.getInstance();
        b.setTime(birthdate);
        int bYear = b.get(Calendar.YEAR);
        int bMonth = b.get(Calendar.MONTH);
        int bDay = b.get(Calendar.DAY_OF_MONTH);

        Calendar c = Calendar.getInstance();
        c.set(aYear, bMonth, bDay);

        if (!a.after(a)) {
            return aYear - bYear;
        } else {
            return aYear - bYear - 1;
        }
    }


    public static String dateStr(Calendar c) {
        return c.get(Calendar.YEAR) + "-" + c.get(Calendar.DAY_OF_YEAR);
    }

    public static String dateStr(Date d) {
        return dateStr(d, 0, false);
    }

    public static String dateStr(Date d, int monthIndex) {
        return dateStr(d, monthIndex, false);
    }

    public static String dateStr(Date d, int monthIndex, boolean isEndDayOfMonth) {
        Calendar a = Calendar.getInstance();
        a.setTime(d);
        if (monthIndex > 0) {
            a.add(Calendar.MONTH, monthIndex);
        }

        if (isEndDayOfMonth) {
            a.add(Calendar.MONTH, 1);
            a.add(Calendar.DAY_OF_MONTH, -1);
        }

        return a.get(Calendar.YEAR) + "-" + a.get(Calendar.DAY_OF_YEAR);
    }


    public static int compareTo(Calendar a, int endYear, int endDayOfYear) {
        int year = a.get(Calendar.YEAR);
        if (year < endYear) {
            return -1;
        } else if (year == endYear) {
            int dayOfYear = a.get(Calendar.DAY_OF_YEAR);
            if (dayOfYear < endDayOfYear) {
                return -1;
            } else if (dayOfYear == endDayOfYear) {
                return 0;
            }
        }
        return 1;
    }
}
