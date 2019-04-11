package com.github.nijian.jkeel.report.jasper;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JasperReportFactory
 *
 * @author nj
 * @since 0.0.2
 */
public final class JasperReportFactory extends BaseKeyedPooledObjectFactory<String, JasperReport> {

    private static Logger logger = LoggerFactory.getLogger(JasperReportFactory.class);

    @Override
    public JasperReport create(String rptURL) throws Exception {
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(rptURL));

//        JasperDesign design = JRXmlLoader.load(rptURL);
//        JasperReportProxy jasperReport = JasperCompileManager.compileReport(design);
        logger.info("A Jasper report instance is created with template {}", rptURL);
        return jasperReport;
    }

    @Override
    public PooledObject<JasperReport> wrap(JasperReport value) {
        return new DefaultPooledObject<>(value);
    }

    @Override
    public boolean validateObject(String rptURL, PooledObject<JasperReport> p) {
        return true;
    }

    @Override
    public void destroyObject(String rptURL, PooledObject<JasperReport> p) {
        logger.info("The Jasper report instance {} will be destroyed", rptURL);
    }

}