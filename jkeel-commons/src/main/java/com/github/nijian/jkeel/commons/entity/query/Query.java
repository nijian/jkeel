package com.github.nijian.jkeel.commons.entity.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.nijian.jkeel.commons.entity.query.sql.SqlQuery;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = SqlQuery.class, name = "sql")})
public abstract class Query<T extends Condition> {

    private String type;

    private Long pageNum;

    private Integer pageSize;

    private List<T> conditionList;

    private boolean withCount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<T> conditionList) {
        this.conditionList = conditionList;
    }

    public boolean isWithCount() {
        return withCount;
    }

    public void setWithCount(boolean withCount) {
        this.withCount = withCount;
    }
}
