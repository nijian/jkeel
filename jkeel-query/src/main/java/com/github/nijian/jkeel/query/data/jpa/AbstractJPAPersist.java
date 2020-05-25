package com.github.nijian.jkeel.query.data.jpa;

import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.query.data.Persist;

public abstract class AbstractJPAPersist extends Persist {

    @Override
    protected void prePersist(ServiceContext ctx) {
        if (!ctx.isUseJPA()) {
            ctx.setUseJPA(true);
        }
        ctx.setNeedSyncData(true);
    }
}
