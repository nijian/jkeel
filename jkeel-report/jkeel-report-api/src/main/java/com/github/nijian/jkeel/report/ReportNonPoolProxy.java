package com.github.nijian.jkeel.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;


public abstract class ReportNonPoolProxy<P extends ExportParams> implements ReportProxy {

    private static Logger logger = LoggerFactory.getLogger(ReportNonPoolProxy.class);

    protected abstract P buildPrintParams(String paramsJson);

    protected abstract void printToStream(String rptURI, P printParams, OutputStream outputStream, int remainTries);

    protected abstract ReportMeta printToFile(String rptURI, P printParams, int remainTries);

    @Override
    public void exportToStream(String rptURI, String paramsJson, OutputStream outputStream) {
        logger.info("Start to print report with template {} to stream", rptURI);
        printToStream(rptURI, buildPrintParams(paramsJson), outputStream, 3);
        logger.info("End to print report to stream");
    }

    @Override
    public ReportMeta exportToFile(String rptURI, String paramsJson) {
        logger.info("Start to print report with template {} to file", rptURI);
        ReportMeta reportMeta = printToFile(rptURI, buildPrintParams(paramsJson), 3);
        logger.info("End to print report to file");
        return reportMeta;
    }
}
