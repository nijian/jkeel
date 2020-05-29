package com.github.nijian.jkeel.code.i18n;

import com.github.nijian.jkeel.code.entity.Code;
import com.github.nijian.jkeel.commons.feature.Feature;
import com.github.nijian.jkeel.commons.feature.FeatureBinder;
import com.github.nijian.jkeel.commons.feature.FeatureParam;
import com.github.nijian.jkeel.i18n.I18nFeature;

public class CodeI18nBinder extends FeatureBinder<Code, String> {

    private I18nFeature i18nFeature;

    public CodeI18nBinder(I18nFeature feature) {
        this.i18nFeature = feature;
    }

    @Override
    protected FeatureParam prepareParam(Code code) {
        return null;
    }

    @Override
    protected void bind(Code code, String displayName) {
        code.setDisplayName(displayName);
    }

    @Override
    public Feature getFeature() {
        return i18nFeature;
    }
}
