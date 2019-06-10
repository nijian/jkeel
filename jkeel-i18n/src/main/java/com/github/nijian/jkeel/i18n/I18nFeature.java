package com.github.nijian.jkeel.i18n;

import com.github.nijian.jkeel.commons.feature.Feature;

public class I18nFeature implements Feature<String, I18nFeatureParam> {

    @Override
    public String apply(I18nFeatureParam param) {

        return "hello world";
    }

}
