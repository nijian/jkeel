package com.github.nijian.jkeel.commons.entity.query;

import java.util.List;

public class QueryDSL {

    private String dsl;

    private List<Object> args;

    public String getDsl() {
        return dsl;
    }

    public void setDsl(String dsl) {
        this.dsl = dsl;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }
}
