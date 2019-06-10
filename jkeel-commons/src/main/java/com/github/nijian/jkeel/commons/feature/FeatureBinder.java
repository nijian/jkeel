package com.github.nijian.jkeel.commons.feature;

public interface FeatureBinder<T, O> {

    FeatureParam prepareParam(O obj);

    Feature<T, FeatureParam> getFeature();
}
