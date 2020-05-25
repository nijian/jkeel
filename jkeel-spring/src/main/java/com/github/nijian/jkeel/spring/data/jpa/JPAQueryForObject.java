package com.github.nijian.jkeel.spring.data.jpa;

import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.query.data.jpa.AbstractJPALoad;
import com.github.nijian.jkeel.spring.SpringManager;

import javax.persistence.EntityManager;

public class JPAQueryForObject extends AbstractJPALoad {

    @Override
    protected Object doLoad(ServiceContext ctx, Class<?> returnClass, Object identifier) {
        SpringManager manager = (SpringManager) ctx.getManager();
        EntityManager entityManager = manager.getEntityManager();

        return entityManager.find(returnClass, identifier);
    }

    @Override
    protected void sync(ServiceContext ctx) {
        SpringManager manager = (SpringManager) ctx.getManager();
        EntityManager entityManager = manager.getEntityManager();
        entityManager.flush();
    }
}
