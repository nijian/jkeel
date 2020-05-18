package com.github.nijian.jkeel.commons.entity.query.sql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.commons.entity.query.Condition;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SqlFuzzyCondition extends SqlCondition<String> {

    @Override
    public String toDSL() {
        return null;
    }
}
