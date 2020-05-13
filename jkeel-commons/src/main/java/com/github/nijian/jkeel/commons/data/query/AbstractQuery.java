package com.github.nijian.jkeel.commons.data.query;

import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.commons.entity.query.QueryResult;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;

public abstract class AbstractQuery extends DataAccessor {

    @Override
    protected QueryResult execute(BehaviorInput behaviorInput) {
        ServiceContext<?> ctx = behaviorInput.getContext();
        QueryResult queryResult = new QueryResult();

        DataAccessorConfig dataAccessorConfig = (DataAccessorConfig) behaviorInput.getConfigItem();

        Query query = new Query();//queryBehaviorInput.getValue();


        Object[] args = new Object[1];
        return execute0(ctx, queryResult, query.getSelect(), args);
    }

    protected abstract QueryResult execute0(ServiceContext<?> serviceContext, QueryResult queryResult, String queryDsl, Object[] args);
}
