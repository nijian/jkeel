package com.github.nijian.jkeel.commons.json.service;

import com.github.nijian.jkeel.commons.JsonString;
import com.github.nijian.jkeel.commons.ObjectHolder;
import com.github.nijian.jkeel.concept.Service;

public final class JsonService extends Service<JsonString> {

    @Override
    protected JsonString handleResult(Object f) {
        try {
            return new JsonString(ObjectHolder.objectMapper.writeValueAsString(f));
        } catch (Exception e) {
            throw new RuntimeException("xxx");
        }
    }
}
