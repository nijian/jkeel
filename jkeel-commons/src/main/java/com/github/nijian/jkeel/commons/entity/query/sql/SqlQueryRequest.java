package com.github.nijian.jkeel.commons.entity.query.sql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.commons.entity.query.QueryRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SqlQueryRequest extends QueryRequest<SqlCondition<?>> {

}
