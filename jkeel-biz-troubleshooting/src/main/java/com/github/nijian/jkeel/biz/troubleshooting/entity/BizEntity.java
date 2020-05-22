package com.github.nijian.jkeel.biz.troubleshooting.entity;

public abstract class BizEntity {

    private BizEntityInfo info;

    public BizEntityInfo getInfo() {
        return info;
    }

    public void setInfo(BizEntityInfo info) {
        this.info = info;
    }
}
