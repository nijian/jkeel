package com.github.nijian.jkeel.report;

public abstract class ReportExtProperties {

    /**
     * Min size of crystal report client document instance pool
     */
    private int minSize = 4;

    /**
     * Max size of crystal report client document instance pool
     */
    private int maxSize = 8;

    /**
     * Get min size of crystal report client document instance pool
     *
     * @return min size of crystal report client document instance pool
     */
    public int getMinSize() {
        return minSize;
    }

    /**
     * Set min size of crystal report client document instance pool
     *
     * @param minSize min size of crystal report client document instance pool
     */
    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    /**
     * Get max size of crystal report client document instance pool
     *
     * @return max size of crystal report client document instance pool
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Set max size of crystal report client document instance pool
     *
     * @param maxSize max size of crystal report client document instance pool
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

}
