package com.github.nijian.jkeel.algorithms.subsection.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Row<T> {

    private BigDecimal value;

    private BigDecimal rate;

    private BigDecimal ratio;

    /**
     * Date
     * Integer
     */
    private String xaxisType;

    private T start;

    private T end;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public String getXaxisType() {
        return xaxisType;
    }

    public void setXaxisType(String xaxisType) {
        this.xaxisType = xaxisType;
    }

    public T getStart() {
        return start;
    }

    public void setStart(T start) {
        this.start = start;
    }

    public T getEnd() {
        return end;
    }

    public void setEnd(T end) {
        this.end = end;
    }

    @Override
    public Row clone() {


        return null;
    }

    public void calc(BigDecimal inputValue, T current) {
        ratio = calcRatio();
        value = inputValue.multiply(rate).multiply(ratio);
        end = current;
    }

    protected abstract BigDecimal calcRatio();

}
