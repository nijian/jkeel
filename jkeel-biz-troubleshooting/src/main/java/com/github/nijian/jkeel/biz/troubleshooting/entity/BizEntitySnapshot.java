package com.github.nijian.jkeel.biz.troubleshooting.entity;

import java.util.List;

public class BizEntitySnapshot extends BizEntity {

    private String revision;

    private List<BizEntityPart> partList;

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public List<BizEntityPart> getPartList() {
        return partList;
    }

    public void setPartList(List<BizEntityPart> partList) {
        this.partList = partList;
    }
}
