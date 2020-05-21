package com.github.nijian.jkeel.commons.data.query.jpa;

import com.github.nijian.jkeel.commons.data.query.QueryForObject;
import com.github.nijian.jkeel.concept.ServiceContext;

public abstract class AbstractJPALoad extends QueryForObject {

    @Override
    protected void preLoad(ServiceContext ctx) {
        if (!ctx.isUseJPA()) {
            ctx.setUseJPA(true);
        }
        ctx.setNeedSyncData(true);
    }
}
