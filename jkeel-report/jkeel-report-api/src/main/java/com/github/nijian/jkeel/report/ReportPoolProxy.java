package com.github.nijian.jkeel.report;

import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;

/**
 * ReportPoolProxy
 *
 * @param <T> the type in pool
 * @param <P> ExportParams type
 * @author nj
 * @since 0.0.2
 */
public abstract class ReportPoolProxy<T, P extends ExportParams> implements ReportProxy {

    private static Logger logger = LoggerFactory.getLogger(ReportPoolProxy.class);

    protected boolean init = false;

    protected KeyedObjectPool<String, T> pool;

    /**
     * Initialize ReportPoolProxy
     *
     * @param properties properties
     */
    @Override
    public synchronized void init(String properties) {
        if (!init) {
            doInitPool(properties);
            init = true;
        }
    }

    protected abstract void doInitPool(String properties);

    protected GenericKeyedObjectPoolConfig buildPoolConfig(ReportExtProperties properties) {
        GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        config.setTestOnBorrow(true);
        config.setMinIdlePerKey(properties.getMinSize());
        config.setMaxIdlePerKey(properties.getMaxSize());
        config.setMaxTotalPerKey(properties.getMaxSize());
        config.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        return config;
    }

    protected abstract P buildPrintParams(String paramsJson);

    protected abstract void exportToStream(String rptURI, P printParams, OutputStream outputStream, int remainTries);

    protected abstract ReportMeta exportToFile(String rptURI, P printParams, int remainTries);

    @Override
    public void exportToStream(String rptURI, String paramsJson, OutputStream outputStream) {
        logger.info("Start to print report with template {} to stream", rptURI);
        exportToStream(rptURI, buildPrintParams(paramsJson), outputStream, 3);
        logger.info("End to print report to stream");
    }

    @Override
    public ReportMeta exportToFile(String rptURI, String paramsJson) {
        logger.info("Start to print report with template {} to file", rptURI);
        ReportMeta reportMeta = exportToFile(rptURI, buildPrintParams(paramsJson), 3);
        logger.info("End to print report to file");
        return reportMeta;
    }
}
