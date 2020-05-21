package com.github.nijian.jkeel.spring.data.jpa;

import com.github.nijian.jkeel.commons.data.query.jpa.AbstractJPAPersist;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.spring.SpringManager;

import javax.persistence.EntityManager;

public class JPAPersist extends AbstractJPAPersist {

    @Override
    protected Object doPersist(ServiceContext ctx, Object entity) {
        SpringManager manager = (SpringManager) ctx.getManager();
        EntityManager entityManager = manager.getEntityManager();

        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected void sync(ServiceContext ctx) {

    }
}
