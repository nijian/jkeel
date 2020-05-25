package com.github.nijian.jkeel.commons.entity.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryResult {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long count;

    @JsonInclude(JsonInclude.Include.NON_NULL)
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
