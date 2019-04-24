package com.github.nijian.jkeel.algorithms.subsection.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Snapshot {

    private List<Row> rows;

    private BigDecimal value;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public Snapshot clone() {


        return null;
    }

    public void calc() {
        
    }
}
