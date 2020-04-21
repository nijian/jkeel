package com.github.nijian.jkeel.concept.query.data;

import com.github.nijian.jkeel.concept.ConceptInput;
import com.github.nijian.jkeel.concept.Data;
import com.github.nijian.jkeel.concept.Manager;
import com.github.nijian.jkeel.concept.query.entity.Query;
import com.github.nijian.jkeel.concept.query.entity.QueryResult;

public abstract class AbstractQuery<M extends Manager> extends Data<M, Query, QueryResult> {

    @Override
    public QueryResult apply(ConceptInput<M, Query> queryConceptInput) {

        QueryResult queryResult = new QueryResult();

        Query query = new Query();//queryConceptInput.getValue();


        Object[] args = new Object[1];

        return execute(queryConceptInput.getManager(), queryResult, query.getSelect(), args);
    }

    protected abstract QueryResult execute(M manager, QueryResult queryResult, String queryDsl, Object[] args);
}
