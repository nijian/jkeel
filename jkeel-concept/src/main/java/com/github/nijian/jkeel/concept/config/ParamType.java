package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "paramType")
@XmlEnum
public enum ParamType {
    ORIGINAL,
    REFERENCE,
    CONST_LONG;

    public String value() {
        return name();
    }

    public static ParamType fromValue(String v) {
        return valueOf(v);
    }
}