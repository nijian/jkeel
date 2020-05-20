package com.github.nijian.jkeel.spring.data.sql;

import com.github.nijian.jkeel.commons.data.query.sql.SqlQueryForList;
import com.github.nijian.jkeel.commons.entity.query.QueryResult;
import com.github.nijian.jkeel.concept.ServiceContext;
import com.github.nijian.jkeel.spring.SpringManager;

import java.util.List;

public class SpringDataQueryForList extends SqlQueryForList {


    @Override
    protected QueryResult doQuery(ServiceContext<?> ctx, QueryResult queryResult, String queryDSL, Object... args) {

        SpringManager manager = (SpringManager) ctx.getManager();
        List<?> valueList;
        if (args == null || args.length == 0) {
            valueList = manager.getJdbcTemplate().queryForList(queryDSL);
        } else {
            valueList = manager.getJdbcTemplate().queryForList(queryDSL, args);
        }
        queryResult.setValueList(valueList);

        return queryResult;
    }

    @Override
    protected Long count(ServiceContext<?> ctx, String queryDSL, Object... args) {
        SpringManager manager = (SpringManager) ctx.getManager();
        Long count;
        if (args == null || args.length == 0) {
            count = manager.getJdbcTemplate().queryForObject(queryDSL, Long.class);
        } else {
            count = manager.getJdbcTemplate().queryForObject(queryDSL, args, Long.class);
        }
        return count;
    }
}
