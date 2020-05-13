package com.github.nijian.jkeel.commons.data.query;

import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.commons.entity.query.QueryResult;
import com.github.nijian.jkeel.concept.BehaviorInput;
import com.github.nijian.jkeel.concept.ServiceContext;

public abstract class QueryForList extends AbstractQuery {

    @Override
    public QueryResult execute(BehaviorInput behaviorInput) {
        QueryResult queryResult = super.execute(behaviorInput);
        ServiceContext<?> ctx = behaviorInput.getContext();
        Query query = new Query();//queryBehaviorInput.getValue();
        query.setWithCount(true);

        boolean isWithCount = query.isWithCount();
        String queryDsl = query.getSelect();
        Object[] args = null;//query.getQueryFilterList().toArray();

        if (isWithCount) {
            return appendCount(behaviorInput.getContext(), queryResult, queryDsl, args);
        }

        return queryResult;
    }

    protected QueryResult appendCount(ServiceContext<?> serviceContext, QueryResult queryResult, String queryDsl, Object[] args) {
        throw new RuntimeException("xxxx");
    }
}
