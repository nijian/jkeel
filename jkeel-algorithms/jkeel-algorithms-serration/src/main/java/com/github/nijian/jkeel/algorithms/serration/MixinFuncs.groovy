package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand


/**
 * DO NOT CHANGE THIS CLASS UNLESS YOU ARE CLEAR WHAT EXACT IMPACT FOR PERFORMANCE!!!
 *
 * Created by johnson.ni
 */
trait MixinFuncs {

    /**
     *
     */
    def createGroupIndex(String paramName, int groupCount, String groupByParamName) {
        int len = itemLocationMap.get(groupByParamName).itemInstances.size().intdiv(groupCount)
        for (int i = 1; i < len + 1; i++) {
            outputMap.get(paramName).add(i)
        }
    }

    /**
     *
     */
    def createGroupIndexOnBase(String paramName, int groupCount, int base, String groupByParamName) {
        int len = itemLocationMap.get(groupByParamName).itemInstances.size().intdiv(groupCount)
        for (int i = 0; i < len; i++) {
            outputMap.get(paramName).add(i + base)
        }
    }

    /**
     *
     */
    def putGroupSum(String paramName, int groupCount, String... sourceParamNames) {
        int len = itemLocationMap.get(sourceParamNames[0]).itemInstances.size().intdiv(groupCount)
        for (int i = 0; i < len; i++) {
            def value = 0
            for (int j = 0; j < groupCount; j++) {
                int index = i * groupCount + j
                sourceParamNames.each { pn ->
                    value = value + itemLocationMap.get(pn).itemInstances.get(index).value
                }
            }
            outputMap.get(paramName).add(value)
        }
    }

    def putLast(String paramName, String sourceParamName) {
        List list = itemLocationMap.get(sourceParamName).itemInstances
        outputMap.get(paramName).add(list.get(list.size() - 1).value)
    }

    /**
     *
     */
    def putLGroupSum(String paramName, int groupCount, String... sourceParamNames) {
        int len = itemOutMap.get(sourceParamNames[0]).map.get(sourceParamNames[0]).size().intdiv(groupCount)
        for (int i = 0; i < len; i++) {
            def value = 0
            for (int j = 0; j < groupCount; j++) {
                int index = i * groupCount + j
                sourceParamNames.each { pn ->
                    value = value + itemOutMap.get(pn).map.get(pn).get(index)
                }
            }
            outputMap.get(paramName).add(value)
        }
    }

    def putLGroupFirst(String paramName, int groupCount, String sourceParamName) {
        int len = itemOutMap.get(sourceParamName).map.get(sourceParamName).size().intdiv(groupCount)
        //System.out.println("putLGroupFirst:paramName " + paramName +  ",groupCount:" + groupCount +",sourceParamNames:"+sourceParamName)
        for (int i = 0; i < len; i++) {
            outputMap.get(paramName).add(itemOutMap.get(sourceParamName).map.get(sourceParamName).get(i * groupCount))
        }
    }

    /**
     *
     */
    def putLGroupLast(String paramName, int groupCount, String sourceParamName) {
        int len = itemOutMap.get(sourceParamName).map.get(sourceParamName).size().intdiv(groupCount)
        //System.out.println("putLGroupLast:paramName " + paramName +  ",groupCount:" + groupCount +",sourceParamNames:"+sourceParamName)
        for (int i = 1; i < len + 1; i++) {
            outputMap.get(paramName).add(itemOutMap.get(sourceParamName).map.get(sourceParamName).get(i * groupCount - 1))
        }
    }

    def putLLast(String paramName, String sourceParamName) {
        //System.out.println("putLLast:paramName " + paramName + ",sourceParamNames:"+sourceParamName)
        List list = itemOutMap.get(sourceParamName).map.get(sourceParamName)
        outputMap.get(paramName).add(list.get(list.size() - 1))
    }

    /**
     * Get item value from Parallel Area
     *
     * @param paramName name of item
     * @return value of item
     */
    def get(String paramName) {
        Utils.get(context, paramName)
    }

    /**
     * Get item value from Parallel Area by item index
     *
     * @param index index of item
     * @param paramName name of item
     * @return value of item
     */
    def getx(int index, String paramName) {
        Utils.getx(context, index, paramName)
    }

    /**
     * Get item value from Template Output
     *
     * @param paramName name of item
     * @return value of item
     */
    def getL(String paramName) {
        Utils.getL(context, paramName)
    }

    /**
     * Get item value from Template Output by item index
     *
     * @param index index of item
     * @param paramName name of item
     * @return value of item
     */
    def getLx(int index, String paramName) {
        Utils.getLx(context, index, paramName)
    }

    /**
     * Similar to SUMPRODUCT function in Excel
     *
     * @param index
     * @param offset
     * @param arrayNames
     * @return
     */
    def sumProduct(int index, int offset, String... arrayNames) {
        Utils.sumProduct(context, index, offset, arrayNames)
    }

    /**
     * Similar to SUM function in Excel
     *
     * @param index
     * @param offset
     * @param paraName
     * @return
     */
    def sum(int index, int offset, String paraName) {
        Utils.sum(context, index, offset, paraName)
    }

    /**
     * Calculate by layer
     *
     * @param rateTable rate table of layer
     * @param amount amount
     * @return calculated value
     */
    def layerCalc(BigDecimal[] rateTable, BigDecimalOperand amount) {
        Utils.layerSum(rateTable, amount.value)
    }

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
