package com.github.nijian.jkeel.commons;

import java.util.Map;

public class JsonUtil {

    public static String bean2Json(Object o) {
        try {
            return ObjectHolder.objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException("xx", e);
        }
    }

    public static String map2Json(Map<String, ?> map) {
        try {
            return ObjectHolder.mapMapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException("xx", e);
        }
    }

}
