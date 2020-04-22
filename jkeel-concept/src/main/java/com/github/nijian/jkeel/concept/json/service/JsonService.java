package com.github.nijian.jkeel.concept.json.service;

import com.github.nijian.jkeel.commons.JsonString;
import com.github.nijian.jkeel.commons.ObjectHolder;
import com.github.nijian.jkeel.concept.Manager;
import com.github.nijian.jkeel.concept.Service;

public class JsonService<M extends Manager, T> extends Service<M, T, JsonString> {

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
