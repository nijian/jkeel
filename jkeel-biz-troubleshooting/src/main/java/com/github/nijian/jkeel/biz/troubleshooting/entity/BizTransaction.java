package com.github.nijian.jkeel.biz.troubleshooting.entity;

import java.util.Date;

public class BizTransaction {

    private String term;

    private Date occurrenceDate;

    private Date effectiveDate;

    private BizEntitySnapshot bizEntity;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Date getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(Date occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BizEntitySnapshot getBizEntity() {
        return bizEntity;
    }

    public void setBizEntity(BizEntitySnapshot bizEntity) {
        this.bizEntity = bizEntity;
    }
}
