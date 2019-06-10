package com.github.nijian.jkeel.commons.feature;

import java.util.List;

public abstract class FeatureAcceptable<T, O> {

    private List<FeatureBinder<T, O>> featureBinderList;

    public T getValue(O input) {
        T value = null;
        for (FeatureBinder<T, O> featureBinder : featureBinderList) {
            FeatureParam param = featureBinder.prepareParam(input);
            Feature<T, FeatureParam> feature = featureBinder.getFeature();
            value = feature.apply(param);
        }
        return value;
    }

    public List<FeatureBinder<T, O>> getFeatureBinderList() {
        return featureBinderList;
    }

    public void setFeatureBinderList(List<FeatureBinder<T, O>> featureBinderList) {
        this.featureBinderList = featureBinderList;
    }
}
