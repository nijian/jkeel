package com.github.nijian.jkeel.spring.data.jpa;

import com.github.nijian.jkeel.commons.data.query.QueryForObject;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;
import com.github.nijian.jkeel.spring.SpringManager;

import javax.persistence.EntityManager;

public class JPAQueryForObject extends QueryForObject {

    @Override
    protected Object execute(BehaviorInput behaviorInput) throws Exception {
        ServiceContext<?> ctx = behaviorInput.getContext();
        SpringManager manager = (SpringManager) ctx.getManager();
        EntityManager entityManager = manager.getEntityManager();
        DataAccessorConfig dataAccessorConfig = (DataAccessorConfig) behaviorInput.getConfigItem();
        Class<?> returnClass = Class.forName(dataAccessorConfig.getRclass());
        return entityManager.find(returnClass, 1);//behaviorInput.getValue());
    }
}
