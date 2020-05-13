package com.github.nijian.jkeel.commons;


import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.ServiceConfig;

import java.util.HashMap;
import java.util.Map;

public final class JsonAppender {

    private final Map<String, Object> localJsonMap;

    public JsonAppender() {
        this.localJsonMap = new HashMap<>();
    }

    public void appendBy(ServiceContext<?> ctx) {
        localJsonMap.put(ctx.getServiceEntryName(), ctx.run());
        System.out.println(ctx.rtInfo());
    }

    @Override
    public String toString() {
        return JsonUtil.map2Json(localJsonMap);
    }

}
