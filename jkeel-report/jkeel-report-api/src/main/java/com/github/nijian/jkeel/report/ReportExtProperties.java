package com.github.nijian.jkeel.report;

/**
 * ReportNonPoolProxy use the ext properties to driver different Report tool.
 *
 * @author nj
 * @since 0.0.2
 */
public abstract class ReportExtProperties {

    /**
     * pool min size
     */
    private int minSize = 0;

    /**
     * pool max size
     */
    private int maxSize = 8;

    private long minEvictableIdleTimeMillis;

    private long timeBetweenEvictionRunsMillis;

    /**
     * Get pool min size
     *
     * @return pool min size
     */
    public int getMinSize() {
        return minSize;
    }

    /**
     * Set pool min size
     *
     * @param minSize pool min size
     */
    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    /**
     * Get pool max size
     *
     * @return pool max size
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Set pool max size
     *
     * @param maxSize pool max size
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }
}
