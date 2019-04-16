package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.serration.operands.BigDecimalOperand

/**
 * Provide a set of DSL for algorithm config
 *
 * @author nj
 * @since 0.0.1
 */
trait MixinFuncs {

    def createGroupIndex(String paramName, int groupCount, String groupByParamName) {
        context.createGroupIndex(paramName, groupCount, groupByParamName)
    }

    def createGroupIndexOnBase(String paramName, int groupCount, int base, String groupByParamName) {
        context.createGroupIndexOnBase(paramName, groupCount, base, groupByParamName)
    }

    def putGroupSum(String paramName, int groupCount, String... sourceParamNames) {
        context.putGroupSum(paramName, groupCount, sourceParamNames)
    }

    def putLGroupSum(String paramName, int groupCount, String... sourceParamNames) {
        context.putLGroupSum(paramName, groupCount, sourceParamNames)
    }

    def putLast(String paramName, String sourceParamName) {
        context.putLast(paramName, sourceParamName)
    }

    def putLLast(String paramName, String sourceParamName) {
        context.putLLast(paramName, sourceParamName)
    }

    def putLGroupFirst(String paramName, int groupCount, String sourceParamName) {
        context.putLGroupFirst(paramName, groupCount, sourceParamName)
    }

    def putLGroupLast(String paramName, int groupCount, String sourceParamName) {
        context.putLGroupLast(paramName, groupCount, sourceParamName)
    }

    def get(String paramName) {
        context.get(paramName)
    }

    def getx(int index, String paramName) {
        context.getx(index, paramName)
    }

    def getL(String paramName) {
        context.getL(paramName)
    }

    def getLx(int index, String paramName) {
        context.getLx(index, paramName)
    }

    def sumProduct(int index, int offset, String... arrayNames) {
        context.sumProduct(context, index, offset, arrayNames)
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
        context.sum(context, index, offset, paraName)
    }

    /**
     * Calculate by layer
     *
     * @param rateTable rate table of layer
     * @param amount amount
     * @return calculated value
     */
    def layerCalc(BigDecimal[] rateTable, BigDecimalOperand amount) {
        context.layerSum(rateTable, amount.value)
    }

}
