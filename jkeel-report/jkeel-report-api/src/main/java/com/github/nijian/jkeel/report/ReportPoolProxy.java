package com.github.nijian.jkeel.report;

import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;


public abstract class ReportPoolProxy<T, P extends ExportParams> {

    private static Logger logger = LoggerFactory.getLogger(ReportPoolProxy.class);

    /**
     * init flag of printer
     */
    protected boolean inited = false;

    protected KeyedObjectPool<String, T> pool;


    public void init(String properties) {
        if (!inited) {
            initPool(properties);
            inited = true;
        }
    }

    protected abstract void initPool(String properties);

    protected GenericKeyedObjectPoolConfig buildPoolConfig(ReportExtProperties printerProperties) {
        GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        config.setTestOnBorrow(true);
        config.setMinIdlePerKey(printerProperties.getMinSize());
        config.setMaxIdlePerKey(printerProperties.getMaxSize());
        config.setMaxTotalPerKey(printerProperties.getMaxSize());
        return config;
    }

    protected abstract P buildPrintParams(String paramsJson);

    public void printToStream(String rptURI, String paramsJson, OutputStream outputStream) {
        if (!inited) {
            throw new RuntimeException("The printer has not been initialized");
        }
        logger.info("Start to print report with template {} to stream", rptURI);
        printToStream(rptURI, buildPrintParams(paramsJson), outputStream, 3);
        logger.info("End to print report to stream");
    }

    public ReportMeta printToFile(String rptURI, String paramsJson) {
        if (!inited) {
            throw new RuntimeException("The printer has not been initialized");
        }
        logger.info("Start to print report with template {} to file", rptURI);
        ReportMeta reportMeta = printToFile(rptURI, buildPrintParams(paramsJson), 3);
        logger.info("End to print report to file");
        return reportMeta;
    }

    protected abstract void printToStream(String rptURI, P printParams, OutputStream outputStream, int remainTries);

    protected abstract ReportMeta printToFile(String rptURI, P printParams, int remainTries);

}
