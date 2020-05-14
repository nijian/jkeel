package com.github.nijian.jkeel.commons.entity.query;

import java.util.List;

public class Query {

    private Long pageNum;

    private Integer pageSize;

    private List<QueryFilter> queryFilterList;

    private boolean withCount;

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
