package com.github.nijian.jkeel.spring.data.sql;

import com.github.nijian.jkeel.concept.query.data.Count;
import com.github.nijian.jkeel.concept.query.entity.QueryResult;
import com.github.nijian.jkeel.spring.SpringManager;

public class SpringDataCount extends Count<SpringManager> {

    @Override
    protected QueryResult execute(SpringManager manager, QueryResult queryResult, String queryDsl, Object[] args) {

        Long count = manager.getJdbcTemplate().queryForObject("select count(1) from abc", null, Long.class);
        queryResult.setCount(count);
        return queryResult;
    }

}
