package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.serration.MixinFuncs
import com.github.nijian.jkeel.algorithms.serration.Utils

trait MyMixinFuncs extends MixinFuncs {

    /**
     *
     *
     * @param d1
     * @param d2
     * @return
     */
    def nearestAge(Date d1, Date d2) {
        Utils.nearestAge(d1, d2)
    }

    /**
     *
     *
     * @param isBeginOfMonth
     * @param date
     * @param birthdate
     * @param monthIndex
     * @return
     */
    def actualAge(boolean isBeginOfMonth, Date date, Date birthdate, int monthIndex) {
        Utils.actualAge(isBeginOfMonth, date, birthdate, monthIndex)
    }

    def dateStr(Date d, int monthIndex) {
        Utils.dateStr(d, monthIndex)
    }

    def dateStr(Date d, int monthIndex, boolean isEndDayOfMonth) {
        Utils.dateStr(d, monthIndex, isEndDayOfMonth)
    }

    def addMonthsToDate(baseDate, int months) {
        if (baseDate == null) {
            throw new IllegalArgumentException("Base Date is null, couldn't continue to add Months")
        }

        def realDate
        if (baseDate instanceof Date) {
            realDate = baseDate
        } else if (Func.isDate(baseDate)) {
            realDate = Func.toDate(baseDate)
        } else {
            throw new IllegalArgumentException("Invalid format of Base Date, couldn't continue add Months")
        }

        //System.out.println("addMonthsToDate:baseDate " + Func.to_char(baseDate, "yyyyMMdd") +  ",months:" + months)
        realDate = Func.add_months(realDate, months)

        realDate

    }

    def dateBeforeDays(baseDate, compareDate) {
        if (baseDate == null || compareDate == null) {
            0
        } else {
            if (baseDate == null) {
                throw new IllegalArgumentException("Base Date is null, couldn't continue  compare dates")
            }
            if (compareDate == null) {
                throw new IllegalArgumentException("Base Date is null, couldn't continue  compare dates")
            }

            def realBaseDate
            if (baseDate instanceof Date) {
                realBaseDate = baseDate
            } else if (Func.isDate(baseDate)) {
                realBaseDate = Func.toDate(baseDate)
            } else {
                throw new IllegalArgumentException("Invalid format of Base Date, couldn't continue compare dates")
            }

            def realCompareDate
            if (compareDate instanceof Date) {
                realCompareDate = compareDate
            } else if (Func.isDate(compareDate)) {
                realCompareDate = Func.toDate(compareDate)
            } else {
                throw new IllegalArgumentException("Invalid format of Compare Date, couldn't continue compare dates")
            }

            (realCompareDate.getTime() - realBaseDate.getTime()) / (24 * 60 * 60 * 1000)
        }

    }

    boolean dateIsEqual(baseDate, compareDate) {
        if (baseDate == null) {
            throw new IllegalArgumentException("Base Date is null, couldn't continue  compare dates")
        }
        if (compareDate == null) {
            throw new IllegalArgumentException("Base Date is null, couldn't continue  compare dates")
        }

        def realBaseDate
        if (baseDate instanceof Date) {
            realBaseDate = baseDate
        } else if (Func.isDate(baseDate)) {
            realBaseDate = Func.toDate(baseDate)
        } else {
            throw new IllegalArgumentException("Invalid format of Base Date, couldn't continue compare dates")
        }

        def realCompareDate
        if (compareDate instanceof Date) {
            realCompareDate = compareDate
        } else if (Func.isDate(compareDate)) {
            realCompareDate = Func.toDate(compareDate)
        } else {
            throw new IllegalArgumentException("Invalid format of Compare Date, couldn't continue compare dates")
        }

        !realBaseDate.before(realCompareDate) && !realCompareDate.before(realBaseDate)
    }

    int monthsBetween(Date baseDate, Date compareDate) {
        Calendar baseCalender = Calendar.getInstance()
        baseCalender.setTime(baseDate)

        Calendar compareCalender = Calendar.getInstance()
        compareCalender.setTime(compareDate)

        def years = compareCalender.get(Calendar.YEAR) - baseCalender.get(Calendar.YEAR)

        def months = compareCalender.get(Calendar.MONTH) - baseCalender.get(Calendar.MONTH)
        Math.abs(years * 12 + months)
    }

    String formatDateWithddMMMyyyy(Date date) {
        if (date == null) {
            return null
        }
        Func.to_char(date, 'dd/MM/yyyy')
    }
}
