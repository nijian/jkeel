package com.github.nijian.jkeel.commons.entity.query.sql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SqlEqualCondition extends SqlCondition<Object> {

    @Override
    public String toDSL() {
        StringBuffer sb = new StringBuffer();
        sb.append(getName()).append("=");
        Object value = getValue();
        if (value instanceof String) {
            sb.append("\"").append(value).append("\"");
        } else {
            sb.append(value);
        }
        return sb.toString();
    }
}
