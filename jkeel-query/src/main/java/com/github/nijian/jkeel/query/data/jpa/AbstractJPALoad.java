package com.github.nijian.jkeel.query.data.jpa;

import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.query.data.QueryForObject;

public abstract class AbstractJPALoad extends QueryForObject {

    @Override
    protected void preLoad(ServiceContext ctx) {
        if (!ctx.isUseJPA()) {
            ctx.setUseJPA(true);
        }
        ctx.setNeedSyncData(true);
    }
}
