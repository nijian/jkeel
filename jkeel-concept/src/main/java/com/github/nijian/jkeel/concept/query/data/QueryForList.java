package com.github.nijian.jkeel.concept.query.data;

import com.github.nijian.jkeel.concept.ConceptInput;
import com.github.nijian.jkeel.concept.Manager;
import com.github.nijian.jkeel.concept.query.entity.Query;
import com.github.nijian.jkeel.concept.query.entity.QueryResult;

public abstract class QueryForList<M extends Manager> extends AbstractQuery<M> {

    @Override
    public QueryResult apply(ConceptInput<M, Query> queryConceptInput) {

        QueryResult queryResult = super.apply(queryConceptInput);

        Query query = new Query();//queryConceptInput.getValue();
        query.setWithCount(true);

        boolean isWithCount = query.isWithCount();
        String queryDsl = query.getSelect();
        Object[] args = null;//query.getQueryFilterList().toArray();

        if (isWithCount) {
            return appendCount(queryConceptInput.getManager(), queryResult, queryDsl, args);
        }

        return queryResult;
    }

    protected QueryResult appendCount(M manager, QueryResult queryResult, String queryDsl, Object[] args) {
        throw new RuntimeException("xxxx");
    }
}
