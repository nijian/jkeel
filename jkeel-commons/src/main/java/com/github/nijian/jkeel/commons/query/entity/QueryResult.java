package com.github.nijian.jkeel.commons.query.entity;

import java.util.List;

public class QueryResult {

    private Long count;

    private Long pageNum;

    private List<?> valueList;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public List<?> getValueList() {
        return valueList;
    }

    public void setValueList(List<?> valueList) {
        this.valueList = valueList;
    }
}
