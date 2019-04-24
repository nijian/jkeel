package com.github.nijian.jkeel.algorithms.subsection.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Input<T> {

    private BigDecimal value;

    private BigDecimal rate;

    /**
     * Date
     * Integer
     */
    private String xaxisType;

    private T current;

    private Data data;

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

    public String getXaxisType() {
        return xaxisType;
    }

    public void setXaxisType(String xaxisType) {
        this.xaxisType = xaxisType;
    }

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
