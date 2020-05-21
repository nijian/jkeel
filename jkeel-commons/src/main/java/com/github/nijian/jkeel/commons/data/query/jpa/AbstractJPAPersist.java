package com.github.nijian.jkeel.commons.data.query.jpa;

import com.github.nijian.jkeel.commons.data.query.Persist;
import com.github.nijian.jkeel.concept.ServiceContext;

public abstract class AbstractJPAPersist extends Persist {

    @Override
    protected void prePersist(ServiceContext ctx) {
        if (!ctx.isUseJPA()) {
            ctx.setUseJPA(true);
        }
        ctx.setNeedSyncData(true);
    }
}
