package com.github.nijian.jkeel.commons.query.data;

import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.commons.query.entity.Query;
import com.github.nijian.jkeel.commons.query.entity.QueryResult;

public abstract class QueryForList extends AbstractQuery {

    @Override
    public QueryResult apply(BehaviorInput queryBehaviorInput) {

        QueryResult queryResult = super.apply(queryBehaviorInput);

        Query query = new Query();//queryBehaviorInput.getValue();
        query.setWithCount(true);

        boolean isWithCount = query.isWithCount();
        String queryDsl = query.getSelect();
        Object[] args = null;//query.getQueryFilterList().toArray();

        if (isWithCount) {
            return appendCount(queryBehaviorInput.getContext(), queryResult, queryDsl, args);
        }

        return queryResult;
    }

    protected QueryResult appendCount(ServiceContext<?> serviceContext, QueryResult queryResult, String queryDsl, Object[] args) {
        throw new RuntimeException("xxxx");
    }
}
