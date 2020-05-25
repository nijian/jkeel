package com.github.nijian.jkeel.commons.data.query;

import com.github.nijian.jkeel.commons.entity.query.QueryRequest;
import com.github.nijian.jkeel.commons.entity.query.QueryDSL;
import com.github.nijian.jkeel.commons.entity.query.QueryResult;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;

import java.util.List;

public abstract class QueryForList extends DataAccessor<QueryResult> {

    @Override
    public final QueryResult execute(BehaviorInput behaviorInput) {

        QueryResult queryResult = new QueryResult();

        QueryRequest queryRequest = (QueryRequest) behaviorInput.getValue();
        ServiceContext ctx = behaviorInput.getContext();
        DataAccessorConfig dataAccessorConfig = (DataAccessorConfig) behaviorInput.getConfigItem();

        long count = -1l;
        QueryDSL queryDSL;
        List<Object> args;

        if (queryRequest.isWithCount()) {
            queryDSL = generateCountDSL(dataAccessorConfig, queryRequest);
            args = queryDSL.getArgs();
            if (args == null) {
                count = count(ctx, queryDSL.getDsl());
            } else {
                count = count(ctx, queryDSL.getDsl(), args.toArray());
            }
            queryResult.setCount(count);
        }

        if (count != 0) {
            queryDSL = generateQueryDSL(dataAccessorConfig, queryRequest);
            args = queryDSL.getArgs();
            if (args == null) {
                queryResult = doQuery(ctx, queryResult, queryDSL.getDsl());
            } else {
                queryResult = doQuery(ctx, queryResult, queryDSL.getDsl(), args.toArray());
            }
        }

        return queryResult;
    }

    // QueryRequest to Sql DSL and args
    protected abstract QueryDSL generateQueryDSL(DataAccessorConfig dataAccessorConfig, QueryRequest queryRequest);

    // QueryRequest to Sql DSL for count
    protected abstract QueryDSL generateCountDSL(DataAccessorConfig dataAccessorConfig, QueryRequest queryRequest);

    protected abstract QueryResult doQuery(ServiceContext ctx, QueryResult queryResult, String queryDSL, Object... args);

    protected abstract Long count(ServiceContext ctx, String queryDSL, Object... args);

}
