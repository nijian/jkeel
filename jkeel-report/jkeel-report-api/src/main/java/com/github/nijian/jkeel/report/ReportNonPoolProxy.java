package com.github.nijian.jkeel.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;


public abstract class ReportNonPoolProxy<P extends ExportParams> implements ReportProxy {

    private static Logger logger = LoggerFactory.getLogger(ReportNonPoolProxy.class);

    protected boolean init = false;

    protected abstract void doInit(String properties);

    protected abstract P buildPrintParams(String paramsJson);

    protected abstract void exportToStream(String rptURI, P printParams, OutputStream outputStream, int remainTries);

    protected abstract ReportMeta exportToFile(String rptURI, P printParams, int remainTries);

    @Override
    public synchronized void init(String properties) {
        if (!init) {
            doInit(properties);
            init = true;
        }
    }

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
