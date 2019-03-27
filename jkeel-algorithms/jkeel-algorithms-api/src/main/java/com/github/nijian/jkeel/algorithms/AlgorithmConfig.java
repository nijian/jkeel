package com.github.nijian.jkeel.algorithms;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by Johnson.Ni
 */
public interface AlgorithmConfig extends Serializable {

    boolean isDebugEnabled();

    void init(String cid, String configUri, Properties env);

    String getCid();

}
