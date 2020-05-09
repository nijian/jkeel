package com.github.nijian.jkeel.commons.service;

import com.github.nijian.jkeel.commons.JsonString;
import com.github.nijian.jkeel.commons.ObjectHolder;
import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.ServiceContext;

public final class JsonService extends Service<JsonString> {

    @Override
    protected JsonString handleResult(ServiceContext<?> ctx, ConfigItem<?> currentBehaviorConfig, Object value) {
        try {
            return new JsonString(ObjectHolder.objectMapper.writeValueAsString(value));
        } catch (Exception e) {
            throw new RuntimeException("xxx");
        }
    }
}
