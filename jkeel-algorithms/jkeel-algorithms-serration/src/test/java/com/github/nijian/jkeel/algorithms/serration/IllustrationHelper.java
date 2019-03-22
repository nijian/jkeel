package com.github.nijian.jkeel.algorithms.serration;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Special Business logic for Baoviet
 */
public final class IllustrationHelper {


    private final static BigDecimal[] vH = new BigDecimal[]{new BigDecimal(Math.pow(1.07, 1.0 / 366)), new BigDecimal(Math.pow(1.07, 1.0 / 365))};

    private final static BigDecimal[] vL = new BigDecimal[]{new BigDecimal(Math.pow(1.05, 1.0 / 366)), new BigDecimal(Math.pow(1.05, 1.0 / 365))};

    private final static BigDecimal[] vG = new BigDecimal[]{new BigDecimal(Math.pow(1.05, 1.0 / 366)), new BigDecimal(Math.pow(1.05, 1.0 / 365)),
            new BigDecimal(Math.pow(1.045, 1.0 / 365)), new BigDecimal(Math.pow(1.045, 1.0 / 365)),
            new BigDecimal(Math.pow(1.04, 1.0 / 365)), new BigDecimal(Math.pow(1.04, 1.0 / 365)),
            new BigDecimal(Math.pow(1.04, 1.0 / 365)), new BigDecimal(Math.pow(1.04, 1.0 / 365)),
            new BigDecimal(Math.pow(1.035, 1.0 / 365)), new BigDecimal(Math.pow(1.035, 1.0 / 365)),
            new BigDecimal(Math.pow(1.03, 1.0 / 365)), new BigDecimal(Math.pow(1.03, 1.0 / 365)),
            new BigDecimal(Math.pow(1.03, 1.0 / 365)), new BigDecimal(Math.pow(1.03, 1.0 / 365)),
            new BigDecimal(Math.pow(1.03, 1.0 / 365)), new BigDecimal(Math.pow(1.03, 1.0 / 365)),
            new BigDecimal(Math.pow(1.03, 1.0 / 365)), new BigDecimal(Math.pow(1.03, 1.0 / 365)),
            new BigDecimal(Math.pow(1.03, 1.0 / 365)), new BigDecimal(Math.pow(1.03, 1.0 / 365)),
            new BigDecimal(Math.pow(1.02, 1.0 / 365)), new BigDecimal(Math.pow(1.02, 1.0 / 365))
    };

    private final static double[] v0H = new double[]{Math.pow(1.07, 1.0 / 366), Math.pow(1.07, 1.0 / 365)};

    private final static double[] v0L = new double[]{Math.pow(1.05, 1.0 / 366), Math.pow(1.05, 1.0 / 365)};

    private final static double[] v0G = new double[]{Math.pow(1.05, 1.0 / 366), Math.pow(1.05, 1.0 / 365),
            Math.pow(1.045, 1.0 / 365), Math.pow(1.045, 1.0 / 365),
            Math.pow(1.04, 1.0 / 365), Math.pow(1.04, 1.0 / 365),
            Math.pow(1.04, 1.0 / 365), Math.pow(1.04, 1.0 / 365),
            Math.pow(1.035, 1.0 / 365), Math.pow(1.035, 1.0 / 365),
            Math.pow(1.03, 1.0 / 365), Math.pow(1.03, 1.0 / 365),
            Math.pow(1.03, 1.0 / 365), Math.pow(1.03, 1.0 / 365),
            Math.pow(1.03, 1.0 / 365), Math.pow(1.03, 1.0 / 365),
            Math.pow(1.03, 1.0 / 365), Math.pow(1.03, 1.0 / 365),
            Math.pow(1.03, 1.0 / 365), Math.pow(1.03, 1.0 / 365),
            Math.pow(1.02, 1.0 / 365), Math.pow(1.02, 1.0 / 365)
    };

    /**
     * From illustration date to age 100
     *
     * @param illusDate
     * @param birthDate
     * @param hlg       H, L, G
     * @return dynamic unit price rate table
     */
    public static Map<String, BigDecimal> buildUnitPriceRateTable(Date illusDate, Date birthDate, BigDecimal startValue, char hlg) {

        Map<String, BigDecimal> retMap = new HashMap<String, BigDecimal>();
        BigDecimal[] v;
        if (hlg == 'H') {
            v = vH;
        } else if (hlg == 'L') {
            v = vL;
        } else {
            v = vG;
        }
        Calendar i = Calendar.getInstance();
        i.setTime(illusDate);
        int originalMonth = i.get(Calendar.MONTH);
        int originalDayOfMonth = i.get(Calendar.DAY_OF_MONTH);

        Calendar b = Calendar.getInstance();
        b.setTime(birthDate);
        b.add(Calendar.YEAR, 100);
        int endYear = b.get(Calendar.YEAR);
        int endDayOfYear = b.get(Calendar.DAY_OF_YEAR);

        int yearIndex = 0; //first day will be ignored
        BigDecimal currentValue = null;
        while (Utils.compareTo(i, endYear, endDayOfYear) < 0) {
            int year = i.get(Calendar.YEAR);
            int month = i.get(Calendar.MONTH);
            int dayOfYear = i.get(Calendar.DAY_OF_YEAR);
            int dayOfMonth = i.get(Calendar.DAY_OF_MONTH);
            if (currentValue == null) {
                currentValue = startValue;
            } else {
                int vIndex = ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? 0 : 1;
                if (hlg == 'G') {
                    if (yearIndex >= 10) {
                        currentValue = currentValue.multiply(v[10 * 2 + vIndex]).setScale(6, BigDecimal.ROUND_HALF_UP);
                    } else {
                        if (month == originalMonth && dayOfMonth == originalDayOfMonth) {
                            yearIndex++;
                        }
                        currentValue = currentValue.multiply(v[yearIndex * 2 + vIndex]).setScale(6, BigDecimal.ROUND_HALF_UP);
                    }
                } else {
                    currentValue = currentValue.multiply(v[vIndex]).setScale(6, BigDecimal.ROUND_HALF_UP);
                }
            }
            retMap.put(year + "-" + dayOfYear, currentValue);
            i.add(Calendar.DATE, 1);
        }

        return retMap;
    }

    public static Map<String, Double> buildUnitPriceRateTable0(Date illusDate, Date birthDate, double startValue, char hlg) {

        Map<String, Double> retMap = new HashMap<String, Double>();
        double[] v;
        if (hlg == 'H') {
            v = v0H;
        } else if (hlg == 'L') {
            v = v0L;
        } else {
            v = v0G;
        }
        Calendar i = Calendar.getInstance();
        i.setTime(illusDate);
        int originalMonth = i.get(Calendar.MONTH);
        int originalDayOfMonth = i.get(Calendar.DAY_OF_MONTH);

        Calendar b = Calendar.getInstance();
        b.setTime(birthDate);
        b.add(Calendar.YEAR, 100);
        int endYear = b.get(Calendar.YEAR);
        int endDayOfYear = b.get(Calendar.DAY_OF_YEAR);

        int yearIndex = 0; //first day will be ignored
        Double currentValue = null;
        while (Utils.compareTo(i, endYear, endDayOfYear) < 0) {
            int year = i.get(Calendar.YEAR);
            int month = i.get(Calendar.MONTH);
            int dayOfYear = i.get(Calendar.DAY_OF_YEAR);
            int dayOfMonth = i.get(Calendar.DAY_OF_MONTH);
            if (currentValue == null) {
                currentValue = startValue;
            } else {
                int vIndex = ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? 0 : 1;
                if (hlg == 'G') {
                    if (yearIndex >= 10) {
                        currentValue = currentValue * v[10 * 2 + vIndex];
                    } else {
                        if (month == originalMonth && dayOfMonth == originalDayOfMonth) {
                            yearIndex++;
                        }
                        currentValue = currentValue * v[yearIndex * 2 + vIndex];
                    }
                } else {
                    currentValue = currentValue * v[vIndex];
                }
            }
            currentValue = Math.round(currentValue * 1000000) / 1000000.0d;
            retMap.put(year + "-" + dayOfYear, currentValue);
            i.add(Calendar.DATE, 1);
        }

        return retMap;
    }

    public final static void main(String[] args) {
        buildUnitPriceRateTable(new Date(118, 7, 24), new Date(94, 1, 1), new BigDecimal(10000), 'G');
    }
}
