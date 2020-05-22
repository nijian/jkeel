package com.github.nijian.jkeel.biz.troubleshooting.entity;

import java.util.List;

public class BizEntityInfo {

    private String term;

    private String identifier;

    private String desc;

    private List<BizTransaction> transacctionList;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<BizTransaction> getTransacctionList() {
        return transacctionList;
    }

    public void setTransacctionList(List<BizTransaction> transacctionList) {
        this.transacctionList = transacctionList;
    }
}
