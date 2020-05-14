package com.github.nijian.jkeel.commons.data.query;

import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.commons.entity.query.QueryResult;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;

// not available for jpa
public abstract class QueryForList extends DataAccessor<QueryResult> {

    @Override
    public final QueryResult execute(BehaviorInput behaviorInput) {

        QueryResult queryResult = new QueryResult();

        ServiceContext<?> ctx = behaviorInput.getContext();
        DataAccessorConfig dataAccessorConfig = (DataAccessorConfig) behaviorInput.getConfigItem();

        //check input class type, TODO just work around
        Query query;
        try {
            query = (Query) behaviorInput.getValue();
        } catch (Exception e) {
            query = new Query();
        }
        query.setWithCount(true);//for test

        // perform real execution
        String queryDSL = generateQueryDSL(dataAccessorConfig, query);
        queryResult = doQuery(ctx, queryResult, queryDSL);

        boolean isWithCount = query.isWithCount();
        if (isWithCount) {
            String countDSL = generateCountDSL(dataAccessorConfig, query);
            queryResult = doQuery(ctx, queryResult, countDSL);
        }

        return queryResult;
    }

    // Query to Sql DSL
    protected abstract String generateQueryDSL(DataAccessorConfig dataAccessorConfig, Query query);

    // Query to Sql DSL for count
    protected abstract String generateCountDSL(DataAccessorConfig dataAccessorConfig, Query query);

    protected abstract QueryResult doQuery(ServiceContext<?> ctx, QueryResult queryResult, String queryDSL);

}
