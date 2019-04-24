package com.github.nijian.jkeel.algorithms.subsection.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Data<T> {

    //// input

    private BigDecimal value;

    private BigDecimal rate;

    /**
     * Date
     * Integer
     */
    private String xaxisType;

    private T current;

    //// calc history

    private List<Snapshot> snapshots;

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

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }

    public BigDecimal calcDelta() {
        return BigDecimal.ZERO;
    }
}
