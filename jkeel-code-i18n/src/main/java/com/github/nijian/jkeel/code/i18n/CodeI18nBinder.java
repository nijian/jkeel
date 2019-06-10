package com.github.nijian.jkeel.code.i18n;

import com.github.nijian.jkeel.code.domain.CodeDef;
import com.github.nijian.jkeel.commons.feature.Feature;
import com.github.nijian.jkeel.commons.feature.FeatureBinder;
import com.github.nijian.jkeel.commons.feature.FeatureParam;
import com.github.nijian.jkeel.i18n.I18nFeature;

public class CodeI18nBinder implements FeatureBinder<String, CodeDef> {

    private I18nFeature i18nFeature;

    public CodeI18nBinder(I18nFeature feature) {
        this.i18nFeature = feature;
    }

    @Override
    public FeatureParam prepareParam(CodeDef codeDef) {
        return null;
    }

    @Override
    public Feature getFeature() {
        return i18nFeature;
    }
}
