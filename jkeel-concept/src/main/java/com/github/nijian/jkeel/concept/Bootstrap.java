package com.github.nijian.jkeel.concept;

public final class Bootstrap {

    private final static String DEFAULT_IMPL = "ACC2";

    private String impl;

    /**
     * singleton instance
     */
    private static Bootstrap instance;

    /**
     * private construction
     */
    private Bootstrap() {

        //TODO
        impl =  "ACC2";

        if(impl==null){
            impl = DEFAULT_IMPL;
        }
    }

    /**
     * Get instance
     *
     * @return instance
     */
    public static Bootstrap getInstance() {
        if (instance == null) {
            synchronized (Bootstrap.class) {
                if (instance == null) {
                    instance = new Bootstrap();
                }
            }
        }
        return instance;
    }

    public String getImpl(){
        return impl;
    }


}
