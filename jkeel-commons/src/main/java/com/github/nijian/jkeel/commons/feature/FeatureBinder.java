package com.github.nijian.jkeel.commons.feature;

public abstract class FeatureBinder<O extends FeatureAcceptable, T> {

    public final void process(O obj) {
        bind(obj, getFeature().apply(prepareParam(obj)));
    }

    protected abstract void bind(O featureAcceptableObj, T featuredObj);

    protected abstract FeatureParam prepareParam(O obj);

    protected abstract Feature<T, FeatureParam> getFeature();


}
