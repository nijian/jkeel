package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.*;

@XmlType(name = "behaviorType")
@XmlEnum
public enum BehaviorType {
    DA,
    CO,
    AC,
    AL,
    SE;

    public String value() {
        return name();
    }

    public static BehaviorType fromValue(String v) {
        return valueOf(v);
    }
}
