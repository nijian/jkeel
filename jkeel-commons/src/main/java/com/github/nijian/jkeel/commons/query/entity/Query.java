package com.github.nijian.jkeel.commons.query.entity;

import java.util.List;

public class Query {

    private Long pageNum;

    private String select;

    private String from;

    private List<QueryFilter> queryFilterList;

    private boolean withCount;

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<QueryFilter> getQueryFilterList() {
        return queryFilterList;
    }

    public void setQueryFilterList(List<QueryFilter> queryFilterList) {
        this.queryFilterList = queryFilterList;
    }

    public boolean isWithCount() {
        return withCount;
    }

    public void setWithCount(boolean withCount) {
        this.withCount = withCount;
    }
}
