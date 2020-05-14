package com.github.nijian.jkeel.commons.data.query;

import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.commons.entity.query.QueryResult;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;

public abstract class QueryForList extends AbstractQuery {

    @Override
    public QueryResult execute(BehaviorInput behaviorInput) {

        QueryResult queryResult = super.execute(behaviorInput);
        ServiceContext<?> ctx = behaviorInput.getContext();
        DataAccessorConfig dataAccessorConfig = (DataAccessorConfig)behaviorInput.getConfigItem();
//        Query query = (Query)behaviorInput.getValue();
//        query.setWithCount(true);
//
//        boolean isWithCount = query.isWithCount();
//        String queryDsl = dataAccessorConfig.getSelect();
//        Object[] args = null;//query.getQueryFilterList().toArray();
//
//        if (isWithCount) {
//            return appendCount(behaviorInput.getContext(), queryResult, queryDsl, args);
//        }

        return queryResult;
    }

    protected QueryResult appendCount(ServiceContext<?> serviceContext, QueryResult queryResult, String queryDsl, Object[] args) {
        throw new RuntimeException("xxxx");
    }
}
