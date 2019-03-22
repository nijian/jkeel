package com.github.nijian.jkeel.algorithms.serration;


import com.github.nijian.jkeel.algorithms.serration.entity.LayoutTemplate;
import groovy.lang.Closure;

import java.util.HashMap;
import java.util.Map;


public class CalcCache {

    private final static Map<String, Map<String, Closure>> closureCache = new HashMap();

    private final static Map<String, LayoutTemplate> templateCache = new HashMap();

    /**
     * initialize closure cache
     *
     * @param region  region of closure cache
     * @param name    name of closure
     * @param closure closure instance
     */
    public static synchronized void put(String region, String name, Closure closure) {
        Map<String, Closure> map = closureCache.get(region);
        if (map == null) {
            map = new HashMap();
            closureCache.put(region, map);
        }
        map.put(name, closure);
    }

    /**
     * initialize layout template cache
     *
     * @param region         key of layout template
     * @param layoutTemplate layout template instance
     */
    public static synchronized void put(String region, LayoutTemplate layoutTemplate) {
        if (!templateCache.containsKey(region)) {
            templateCache.put(region, layoutTemplate);
        }
    }

    public static Closure get(String region, String name) {
        Map<String, Closure> map = closureCache.get(region);
        if (map == null) {
            return null;
        } else {
            return map.get(name);
        }
    }

    public static LayoutTemplate get(String region) {
        return templateCache.get(region);
    }
}
