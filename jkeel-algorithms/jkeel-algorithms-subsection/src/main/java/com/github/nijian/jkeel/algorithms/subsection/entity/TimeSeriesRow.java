package com.github.nijian.jkeel.algorithms.subsection.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeriesRow extends Row<Data> {

    @Override
    protected BigDecimal calcRatio() {


        return BigDecimal.ONE;
    }
}
