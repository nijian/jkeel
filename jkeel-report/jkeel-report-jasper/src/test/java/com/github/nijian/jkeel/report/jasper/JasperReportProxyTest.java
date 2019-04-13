package com.github.nijian.jkeel.report.jasper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nijian.jkeel.report.ReportManager;
import com.github.nijian.jkeel.report.ReportMeta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class JasperReportProxyTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void exportToFile() {
//        try {
//            ReportManager reportManager = ReportManager.getInstance(this.getClass().getResourceAsStream("/config.json"));
//            JasperExportParams jasperExportParams = objectMapper.readValue(this.getClass().getResourceAsStream("/exportparams.json"), JasperExportParams.class);
//            ReportMeta reportMeta = reportManager.exportToFile("/PolicyDocuments.jasper", jasperExportParams.toString());
//            System.out.print(reportMeta.toString());
//        }catch(Exception e){
//            e.printStackTrace();
//        }

    }
}