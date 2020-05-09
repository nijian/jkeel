package com.github.nijian.jkeel.spring.data.sql;

import com.github.nijian.jkeel.commons.data.query.Count;

public abstract class SpringDataCount extends Count {

//    @Override
//    protected QueryResult execute(ServiceContext<?> serviceContext, QueryResult queryResult, String queryDsl, Object[] args) {
//        SpringManager springManager = (SpringManager) serviceContext.getManager();
//        Long count = springManager.getJdbcTemplate().queryForObject("select count(1) from abc", null, Long.class);
//        queryResult.setCount(count);
//        return queryResult;
//    }

}
