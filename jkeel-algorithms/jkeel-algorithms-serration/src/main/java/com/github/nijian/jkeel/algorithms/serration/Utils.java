package com.github.nijian.jkeel.algorithms.serration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nijian.jkeel.algorithms.serration.entity.Context;
import com.github.nijian.jkeel.algorithms.serration.entity.ItemInstance;
import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 * <p>
 * Created by johnson.ni
 */
public final class Utils {

    public final static ObjectMapper objectMapper = new ObjectMapper();

    public static BigDecimalOperand sumProduct(Context<?> context, int index, int offset, String[] arrayNames) {

        BigDecimal retValue = BigDecimal.ZERO;

        int from;
        int to;
        if (offset < 0) {
            from = index + offset;
            to = index;
        } else {
            from = index;
            to = index + offset;
        }

        List<BigDecimalOperand> valuesList = new ArrayList();

        for (int i = 0; i < arrayNames.length; i++) {
            if (context.getItemOutMap().containsKey(arrayNames[i])) {
                valuesList.addAll(context.getItemOutMap().get(arrayNames[i]).getMap().get(arrayNames[i]).subList(from, to + 1));
            } else {
                List<ItemInstance> itemList = context.getItemLocationMap().get(arrayNames[i]).getItemInstances().subList(from, to + 1);
                for (int j = 0; j < itemList.size(); j++) {
                    valuesList.add(itemList.get(j).getValue());
                }
            }
        }

        int len = to - from + 1;
        for (int m = 0; m < len; m++) {
            BigDecimal baseValue = BigDecimal.ONE;
            for (int n = 0; n < arrayNames.length; n++) {
                baseValue = baseValue.multiply(valuesList.get(n * len + m).getValue());
            }
            retValue = retValue.add(baseValue);
        }

        return new BigDecimalOperand(retValue);
    }


    public static BigDecimalOperand sum(Context<?> context, int index, int offset, String paramName) {

        BigDecimal retValue = BigDecimal.ZERO;

        int from;
        int to;
        if (offset < 0) {
            from = index + offset;
            to = index;
        } else {
            from = index;
            to = index + offset;
        }

        List<BigDecimalOperand> valuesList = new ArrayList();
        if (context.getItemOutMap().containsKey(paramName)) {
            valuesList.addAll(context.getItemOutMap().get(paramName).getMap().get(paramName).subList(from, to));
        } else {
            List<ItemInstance> itemList = context.getItemLocationMap().get(paramName).getItemInstances().subList(from, to);
            for (int j = 0; j < itemList.size(); j++) {
                valuesList.add(itemList.get(j).getValue());
            }
        }

        for (int i = 0; i < valuesList.size(); i++) {
            retValue = retValue.add(valuesList.get(i).getValue());
        }

        return new BigDecimalOperand(retValue);
    }


    public static BigDecimal layerSum(BigDecimal[] rateTable, BigDecimal amount) {
        BigDecimal retValue = BigDecimal.ZERO;
        BigDecimal deductedAmount = amount;
        for (int layer = 0; layer * 3 < rateTable.length; layer++) {
            BigDecimal startValue = rateTable[layer * 3];
            BigDecimal endValue = rateTable[layer * 3 + 1];
            BigDecimal rate = rateTable[layer * 3 + 2];
            BigDecimal rangeValue = endValue.subtract(startValue);
            if (endValue.compareTo(BigDecimal.ZERO) < 0 || deductedAmount.compareTo(rangeValue) < 0) {
                return retValue.add(deductedAmount.multiply(rate));
            } else {
                retValue = retValue.add(rangeValue.multiply(rate));
                deductedAmount = deductedAmount.subtract(rangeValue);
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * is nearest age at date
     * Note: if date = nearest passed birthday + 6 month then it will be round up to nearest age.
     * For example: date = 28/02/2018
     * birthdate = 30/08/1989
     * Then Nearest Passed Birthday 30/08/2017
     * 6 months after nearest passed birthday is: 28/02/2018  which is also date
     * => Final age is: 2017 – 1989 + 1  = 29 years old
     *
     * @param date
     * @param birthdate
     * @return
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
