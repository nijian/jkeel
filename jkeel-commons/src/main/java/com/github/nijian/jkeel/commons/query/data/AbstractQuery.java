package com.github.nijian.jkeel.commons.query.data;

import com.github.nijian.jkeel.commons.query.entity.Query;
import com.github.nijian.jkeel.commons.query.entity.QueryResult;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;

public abstract class AbstractQuery extends DataAccessor<QueryResult> {

    @Override
    public QueryResult apply(BehaviorInput queryBehaviorInput) {

        QueryResult queryResult = new QueryResult();

        DataAccessorConfig dataAccessorConfig = (DataAccessorConfig) queryBehaviorInput.getConfigItem();

        Query query = new Query();//queryBehaviorInput.getValue();


        Object[] args = new Object[1];

        return execute(queryBehaviorInput.getContext(), queryResult, query.getSelect(), args);
    }

    protected abstract QueryResult execute(ServiceContext<?> serviceContext, QueryResult queryResult, String queryDsl, Object[] args);
}
