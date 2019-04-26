package com.github.nijian.jkeel.report;

import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ReportManagerTest {

    @Test
    public void mergePDFs() {
        ;

        List<InputStream> pdfFileNames = new ArrayList<>();
        pdfFileNames.add(ReportManagerTest.class.getResourceAsStream("/a.pdf"));
        pdfFileNames.add(ReportManagerTest.class.getResourceAsStream("/b.pdf"));

        try {
            ReportManager.mergePDFStreams("xx.pdf", pdfFileNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}