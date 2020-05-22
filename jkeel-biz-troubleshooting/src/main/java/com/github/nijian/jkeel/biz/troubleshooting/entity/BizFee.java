package com.github.nijian.jkeel.biz.troubleshooting.entity;

import java.math.BigDecimal;

public class BizFee {

    private String term;

    private String currency;

    private BigDecimal amount;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
