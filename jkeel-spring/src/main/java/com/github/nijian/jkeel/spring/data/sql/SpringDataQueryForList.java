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
}
