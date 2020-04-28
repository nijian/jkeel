package com.github.nijian.jkeel.concept.query.data;

import com.github.nijian.jkeel.concept.ConceptInput;
import com.github.nijian.jkeel.concept.Manager;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.query.entity.Query;
import com.github.nijian.jkeel.concept.query.entity.QueryResult;

public abstract class QueryForList extends AbstractQuery {

    @Override
    public QueryResult apply(ConceptInput<? extends Manager, Query> queryConceptInput) {

        QueryResult queryResult = super.apply(queryConceptInput);

        Query query = new Query();//queryConceptInput.getValue();
        query.setWithCount(true);

        boolean isWithCount = query.isWithCount();
        String queryDsl = query.getSelect();
        Object[] args = null;//query.getQueryFilterList().toArray();

        if (isWithCount) {
            return appendCount(queryConceptInput.getContext(), queryResult, queryDsl, args);
        }

        return queryResult;
    }

    protected QueryResult appendCount(ServiceContext<?, ?> serviceContext, QueryResult queryResult, String queryDsl, Object[] args) {
        throw new RuntimeException("xxxx");
    }
}
