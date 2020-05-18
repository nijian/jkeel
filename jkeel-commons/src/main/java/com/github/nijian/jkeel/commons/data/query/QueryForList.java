package com.github.nijian.jkeel.commons.data.query;

import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.commons.entity.query.QueryDSL;
import com.github.nijian.jkeel.commons.entity.query.QueryResult;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;

import java.util.List;

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
            query = new Query() {
                @Override
                public String getType() {
                    return super.getType();
                }

                @Override
                public void setType(String type) {
                    super.setType(type);
                }

                @Override
                public Long getPageNum() {
                    return super.getPageNum();
                }

                @Override
                public void setPageNum(Long pageNum) {
                    super.setPageNum(pageNum);
                }

                @Override
                public Integer getPageSize() {
                    return super.getPageSize();
                }

                @Override
                public void setPageSize(Integer pageSize) {
                    super.setPageSize(pageSize);
                }


                @Override
                public boolean isWithCount() {
                    return super.isWithCount();
                }

                @Override
                public void setWithCount(boolean withCount) {
                    super.setWithCount(withCount);
                }
            };
        }
        query.setWithCount(true);//for test

        // perform real execution
        QueryDSL queryDSL = generateQueryDSL(dataAccessorConfig, query);
        List<Object> args = queryDSL.getArgs();
        if (args == null) {
            queryResult = doQuery(ctx, queryResult, queryDSL.getDsl());
        } else {
            queryResult = doQuery(ctx, queryResult, queryDSL.getDsl(), args.toArray());
        }

        boolean isWithCount = query.isWithCount();
        if (isWithCount) {
            queryDSL = generateCountDSL(dataAccessorConfig, query);
            args = queryDSL.getArgs();
            if (args == null) {
                queryResult = doQuery(ctx, queryResult, queryDSL.getDsl());
            } else {
                queryResult = doQuery(ctx, queryResult, queryDSL.getDsl(), args.toArray());
            }
        }

        return queryResult;
    }

    // Query to Sql DSL and args
    protected abstract QueryDSL generateQueryDSL(DataAccessorConfig dataAccessorConfig, Query query);

    // Query to Sql DSL for count
    protected abstract QueryDSL generateCountDSL(DataAccessorConfig dataAccessorConfig, Query query);

    protected abstract QueryResult doQuery(ServiceContext<?> ctx, QueryResult queryResult, String queryDSL, Object... args);

}
