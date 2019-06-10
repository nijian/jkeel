package com.github.nijian.jkeel.code.domain;

import com.github.nijian.jkeel.commons.feature.FeatureAcceptable;

public class Code extends FeatureAcceptable<String, CodeDef> {

    private CodeDef def;

    public CodeDef getDef() {
        return def;
    }

    public void setDef(CodeDef def) {
        this.def = def;
    }
}
