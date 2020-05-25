package com.github.nijian.jkeel.query.entity.sql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SqlEqualCondition extends SqlCondition<Object> {

    @Override
    public String toDSL(String alias) {
        StringBuffer sb = new StringBuffer();
        if (alias != null) {
            sb.append(alias).append(".");
        }
        sb.append(getName()).append("=? ");
        return sb.toString();
    }
}
