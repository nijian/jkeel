package com.github.nijian.jkeel.algorithms.serration;

/**
 * Constant
 *
 * @author nj
 * @since 0.0.1
 */
public final class Const {

    ///////////// env properties //////////////

    public final static String CACHING_PROVIDER_NAME_KEY = "CachingProvider";

    public final static String CACHING_CONFIG_FILE_NAME_KEY = "CacheConfigFileName";

    public final static String CALC_CLASS_NAME_KEY = "CalcClassName";

    public final static String CALC_CONFIG_CLASS_NAME_KEY = "CalcConfigClassName";

    ///////////// default properties //////////////

    public final static String CACHING_PROVIDER_NAME = "org.ehcache.jsr107.EhcacheCachingProvider";

    public final static String CACHING_CONFIG_FILE_NAME = "/serration-ehcache.xml";

    public final static String CALC_CLASS_NAME = "com.github.nijian.jkeel.algorithms.serration.Calc";

    public final static String CALC_CONFIG_CLASS_NAME = "com.github.nijian.jkeel.algorithms.serration.CalcConfig";

    ///////////// config keys //////////////

    public final static String CONVERT = "Convert";

    public final static String OUTPUT = "Output";

}
