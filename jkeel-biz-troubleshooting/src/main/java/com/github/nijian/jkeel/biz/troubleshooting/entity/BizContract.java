package com.github.nijian.jkeel.biz.troubleshooting.entity;

import java.util.List;

public class BizContract {

    private String identifierTerm;

    private String identifier;

    private String desc;

    private List<BizTransaction> bizTransacctionList;

    public String getIdentifierTerm() {
        return identifierTerm;
    }

    public void setIdentifierTerm(String identifierTerm) {
        this.identifierTerm = identifierTerm;
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

    public List<BizTransaction> getBizTransacctionList() {
        return bizTransacctionList;
    }

    public void setBizTransacctionList(List<BizTransaction> bizTransacctionList) {
        this.bizTransacctionList = bizTransacctionList;
    }


}
