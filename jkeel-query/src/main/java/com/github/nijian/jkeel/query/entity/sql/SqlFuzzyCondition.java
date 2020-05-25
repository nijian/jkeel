package com.github.nijian.jkeel.query.entity.sql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SqlFuzzyCondition extends SqlCondition<String> {

    @Override
    public String toDSL(String alias) {
        return null;
    }
}
