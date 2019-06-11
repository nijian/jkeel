package com.github.nijian.jkeel.commons.feature;

import java.util.List;

public abstract class FeatureAcceptable {

    private List<FeatureBinder> featureBinderList;

    public void applyFeatures() {
        for (FeatureBinder featureBinder : featureBinderList) {
            featureBinder.process(this);
        }
    }

    public List<FeatureBinder> getFeatureBinderList() {
        return featureBinderList;
    }

    public void setFeatureBinderList(List<FeatureBinder> featureBinderList) {
        this.featureBinderList = featureBinderList;
    }
}
