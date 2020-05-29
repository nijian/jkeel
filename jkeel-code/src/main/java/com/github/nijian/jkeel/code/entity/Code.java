package com.github.nijian.jkeel.code.entity;

import com.github.nijian.jkeel.commons.feature.FeatureAcceptable;

public class Code extends FeatureAcceptable {

    private CodeDef codeDef;

    private String displayName;

    public CodeDef getCodeDef() {
        return codeDef;
    }

    public void setCodeDef(CodeDef codeDef) {
        this.codeDef = codeDef;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
