package com.github.nijian.jkeel.algorithms.subsection.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeriesInput extends Input<Date> {

}
