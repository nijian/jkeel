package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "conditionOperator")
@XmlEnum
public enum ConditionOperator {

    FIRST,
    AND,
    OR;

    public String value() {
        return name();
    }

    public static ConditionOperator fromValue(String v) {
        return valueOf(v);
    }
}
