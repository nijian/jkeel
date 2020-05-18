package com.github.nijian.jkeel.commons.entity.query.sql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.nijian.jkeel.commons.entity.query.Condition;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = SqlEqualCondition.class, name = "equal"),
        @JsonSubTypes.Type(value = SqlFuzzyCondition.class, name = "fuzzy"),
        @JsonSubTypes.Type(value = SqlBigDecimalRangerCondition.class, name = "numberRanger"),
        @JsonSubTypes.Type(value = SqlDateRangeCondition.class, name = "dateRanger")})
public abstract class SqlCondition<T> extends Condition<T> {

}
