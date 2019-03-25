package com.github.nijian.jkeel.algorithms;

import javax.cache.Cache;
import java.io.Serializable;

/**
 * Created by Johnson.Ni
 */
public interface Config<T> extends Serializable {

    void init(Cache<String, T> cache);

    String getCid();

}
