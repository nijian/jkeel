package com.github.nijian.jkeel.commons.feature;

public interface Feature<T, P extends FeatureParam> {

    T apply(P param);
}
