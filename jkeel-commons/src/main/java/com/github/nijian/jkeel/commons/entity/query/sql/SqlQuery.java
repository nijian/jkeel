package com.github.nijian.jkeel.commons.entity.query.sql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.commons.entity.query.Query;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SqlQuery extends Query<SqlCondition<?>> {

}
