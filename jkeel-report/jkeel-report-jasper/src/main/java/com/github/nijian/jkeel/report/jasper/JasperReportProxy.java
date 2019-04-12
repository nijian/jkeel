package com.github.nijian.jkeel.report.jasper;

import com.github.nijian.jkeel.commons.ObjectHolder;
import com.github.nijian.jkeel.report.ExportParams;
import com.github.nijian.jkeel.report.ReportMeta;
import com.github.nijian.jkeel.report.ReportPoolProxy;
import com.googlecode.jthaipdf.jasperreports.engine.ThaiExporterManager;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author nj
 * @since 0.0.2
 */
public final class JasperReportProxy extends ReportPoolProxy<JasperReport, JasperExportParams> {

    private static Logger logger = LoggerFactory.getLogger(JasperReportProxy.class);

    @Override
    protected void initPool(String properties) {
        JasperReportProperties printerProperties;
        try {
            printerProperties = ObjectHolder.objectMapper.readValue(properties, JasperReportProperties.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load jasper printer properties", e);
        }
        GenericKeyedObjectPoolConfig config = buildPoolConfig(printerProperties);
        pool = new GenericKeyedObjectPool<>(new JasperReportFactory(), config);
    }

    @Override
    protected JasperExportParams buildPrintParams(String paramsJson) {
        try {
            return ObjectHolder.objectMapper.readValue(paramsJson, JasperExportParams.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse paramsJson for Jasper Report", e);
        }
    }

    @Override
    protected void exportToStream(String rptURI, JasperExportParams printParams, OutputStream outputStream, int remainTries) {
        logger.info("Jasper report pool has {} active instances and {} idle instances for {}", pool.getNumActive(rptURI), pool.getNumIdle(rptURI), rptURI);
        net.sf.jasperreports.engine.JasperReport jasperReport = null;
        try {
            long start = System.currentTimeMillis();
            jasperReport = pool.borrowObject(rptURI);
            JasperPrint jasperPrint = buildJasperPrint(jasperReport, printParams);
            ThaiExporterManager.exportReportToPdfStream(jasperPrint, outputStream);
            logger.info("JasperReportProxy:{} : execution time: {}ms", jasperReport.hashCode(), (System.currentTimeMillis() - start));
        } catch (Exception e) {
            logger.warn("Failed to print to stream by Jasper, remain tries {}", remainTries - 1);
            logger.warn("An exception occurred", e);
            if (remainTries == 1) {
                throw new RuntimeException("Failed to print after 3 tries", e);
            }
            remainTries--;
            exportToStream(rptURI, printParams, outputStream, remainTries);
        } finally {
            try {
                if (jasperReport != null) {
                    pool.returnObject(rptURI, jasperReport);
                }
            } catch (Exception e) {
                logger.warn("Failed to return Jasper report instance {} to pool", jasperReport.hashCode());
            }
        }
    }

    @Override
    protected ReportMeta exportToFile(String rptURI, JasperExportParams printParams, int remainTries) {
        net.sf.jasperreports.engine.JasperReport jasperReport = null;
        try {
            long start = System.currentTimeMillis();
            jasperReport = pool.borrowObject(rptURI);
            JasperPrint jasperPrint = buildJasperPrint(jasperReport, printParams);
            ThaiExporterManager.exportReportToPdfFile(jasperPrint, printParams.getExportFileURI());
            logger.info("JasperReportProxy:{} : execution time: {}ms", jasperReport.hashCode(), (System.currentTimeMillis() - start));
            ReportMeta reportMeta = new ReportMeta();
            reportMeta.setUri(printParams.getExportFileURI());
            return reportMeta;
        } catch (Exception e) {
            logger.warn("Failed to print to file by Jasper, remain tries {}", remainTries - 1);
            logger.warn("An exception occurred", e);
            if (remainTries == 1) {
                throw new RuntimeException("Failed to print after 3 tries", e);
            }
            remainTries--;
            return exportToFile(rptURI, printParams, remainTries);
        } finally {
            try {
                if (jasperReport != null) {
                    pool.returnObject(rptURI, jasperReport);
                }
            } catch (Exception e) {
                logger.warn("Failed to return Jasper report instance {} to pool", jasperReport.hashCode());
            }
        }
    }

    private JasperPrint buildJasperPrint(net.sf.jasperreports.engine.JasperReport jasperReport, JasperExportParams printParams) {
        try {
            String rpDataSource = printParams.getRpDataSource();
            String rpData = StringUtils.removeEnd(StringUtils.removeStart(printParams.getRpData(), "\""), "\"");

            Map<String, Object> params = new HashMap();
            Document document;
            if (rpDataSource.equalsIgnoreCase(ExportParams.RP_DATA_TYPE_URI)) {
                URL url = new URI(rpData).toURL();
                logger.info("Get report data from {}", url.toString());
                document = JRXmlUtils.parse(url);
            } else if (rpDataSource.equalsIgnoreCase(ExportParams.RP_DATA_TYPE_INLINE)) {
                document = JRXmlUtils.parse(IOUtils.toInputStream(rpData, "UTF-8"));
            } else {
                logger.error("The way {} to get report data has not been supported", rpDataSource);
                throw new RuntimeException("Failed to get report data");
            }
            params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
            params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, printParams.getXmlDatePattern());
            params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, printParams.getXmlNumberPattern());
            params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
            params.put(JRParameter.REPORT_LOCALE, Locale.US);
            return JasperFillManager.fillReport(jasperReport, params);
        } catch (Exception e) {
            logger.error("Failed to build Jasper print instance", e);
            throw new RuntimeException("Failed to build Jasper print instance", e);
        }
    }
}
