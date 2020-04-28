package com.github.nijian.jkeel.concept.query.service;

import com.github.nijian.jkeel.concept.Manager;

public class ObjectQueryService extends QueryService<Object> {

    @Override
    protected <F> Object handleResult(F f) {
        return f;
    }
}
