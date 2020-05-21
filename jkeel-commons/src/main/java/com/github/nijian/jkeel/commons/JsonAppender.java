package com.github.nijian.jkeel.commons;

import com.github.nijian.jkeel.concept.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class JsonAppender {

    private static Logger logger = LoggerFactory.getLogger(JsonAppender.class);

    private final Map<String, Object> localJsonMap;

    public JsonAppender() {
        localJsonMap = new HashMap<>();
    }

    public void append(ServiceContext ctx) {
        localJsonMap.put(ctx.getServiceId(), ctx.run());
        System.out.println(ctx.rtInfo());
    }

    @Override
    public String toString() {
        return JsonUtil.map2Json(localJsonMap);
    }

}
