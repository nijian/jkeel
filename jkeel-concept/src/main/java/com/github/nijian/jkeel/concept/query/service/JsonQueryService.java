package com.github.nijian.jkeel.concept.query.service;

import com.github.nijian.jkeel.commons.JsonString;
import com.github.nijian.jkeel.commons.ObjectHolder;
import com.github.nijian.jkeel.concept.Manager;

public final class JsonQueryService extends QueryService<JsonString> {

    @Override
    protected <F> JsonString handleResult(F f) {

        //do mapping
        try {
            return new JsonString(ObjectHolder.objectMapper.writeValueAsString(f));
        } catch (Exception e) {
            throw new RuntimeException("xxx");
        }
    }

}
