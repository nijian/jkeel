package com.github.nijian.jkeel.biz.troubleshooting.entity;

import java.util.List;

public class BizEntityPart {

    private String term;

    private String identifier;

    private String desc;

    private BizFee fee;

    private List<BizEntityPart> childList;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getIdentifier() {
        return identifier;
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

    public BizFee getFee() {
        return fee;
    }

    public void setFee(BizFee fee) {
        this.fee = fee;
    }

    public List<BizEntityPart> getChildList() {
        return childList;
    }

    public void setChildList(List<BizEntityPart> childList) {
        this.childList = childList;
    }
}
