package com.github.nijian.jkeel.query.entity.sql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.query.entity.QueryRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SqlQueryRequest extends QueryRequest<SqlCondition<?>> {

}
