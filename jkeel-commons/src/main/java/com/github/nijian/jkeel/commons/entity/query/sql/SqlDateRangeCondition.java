package com.github.nijian.jkeel.commons.entity.query.sql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.commons.entity.query.Condition;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SqlDateRangeCondition extends SqlCondition<Date> {

    private boolean includeFrom;

    private Date toValue;

    private boolean includeTo;

    public boolean isIncludeFrom() {
        return includeFrom;
    }

    public void setIncludeFrom(boolean includeFrom) {
        this.includeFrom = includeFrom;
    }

    public Date getToValue() {
        return toValue;
    }

    public void setToValue(Date toValue) {
        this.toValue = toValue;
    }

    public boolean isIncludeTo() {
        return includeTo;
    }

    public void setIncludeTo(boolean includeTo) {
        this.includeTo = includeTo;
    }

    @Override
    public String toDSL() {
        return null;
    }
}
