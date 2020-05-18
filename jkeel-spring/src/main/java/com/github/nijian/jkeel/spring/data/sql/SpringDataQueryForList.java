package com.github.nijian.jkeel.spring.data.sql;

import com.github.nijian.jkeel.commons.data.query.sql.SqlQueryForList;
import com.github.nijian.jkeel.commons.entity.query.QueryResult;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.spring.SpringManager;

import java.util.List;

public class SpringDataQueryForList extends SqlQueryForList {


    @Override
    protected QueryResult doQuery(ServiceContext<?> ctx, QueryResult queryResult, String queryDSL) {

        SpringManager manager = (SpringManager) ctx.getManager();
        List<?> valueList = manager.getJdbcTemplate().queryForList(queryDSL);
        queryResult.setValueList(valueList);

        return queryResult;
    }
}
