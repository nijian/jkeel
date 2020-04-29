package com.github.nijian.jkeel.commons.query.data;

import com.github.nijian.jkeel.commons.query.entity.Query;
import com.github.nijian.jkeel.commons.query.entity.QueryResult;
import com.github.nijian.jkeel.concept.ConceptInput;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;

public abstract class AbstractQuery extends DataAccessor<Query, QueryResult> {

    @Override
    public Class<Query> getInputType() {
        return Query.class;
    }

    @Override
    public Class<QueryResult> getReturnType() {
        return QueryResult.class;
    }

    @Override
    public QueryResult apply(ConceptInput<?, Query> queryConceptInput) {

        QueryResult queryResult = new QueryResult();

        DataAccessorConfig dataAccessorConfig = (DataAccessorConfig) queryConceptInput.getConfigItem();

        Query query = new Query();//queryConceptInput.getValue();


        Object[] args = new Object[1];

        return execute(queryConceptInput.getContext(), queryResult, query.getSelect(), args);
    }

    protected abstract QueryResult execute(ServiceContext<?> serviceContext, QueryResult queryResult, String queryDsl, Object[] args);
}
