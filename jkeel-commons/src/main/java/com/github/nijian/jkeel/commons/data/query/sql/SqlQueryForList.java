package com.github.nijian.jkeel.commons.data.query.sql;

import com.github.nijian.jkeel.commons.data.query.QueryForList;
import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;

public abstract class SqlQueryForList extends QueryForList {

    @Override
    protected String generateQueryDSL(DataAccessorConfig dataAccessorConfig, Query query) {
        return null;
    }

    @Override
    protected String generateCountDSL(DataAccessorConfig dataAccessorConfig, Query query) {
        return null;
    }

}
