package com.github.nijian.jkeel.report.jasper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.commons.ObjectHolder;
import com.github.nijian.jkeel.report.ExportParams;

/**
 * JasperExportParams
 *
 * @author nj
 * @since 0.0.2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JasperExportParams extends ExportParams {

    private String xmlDatePattern = "yyyy-MM-dd";

    private String xmlNumberPattern = "#,##0.##";

    public String getXmlDatePattern() {
        return xmlDatePattern;
    }

    public void setXmlDatePattern(String xmlDatePattern) {
        this.xmlDatePattern = xmlDatePattern;
    }

    public String getXmlNumberPattern() {
        return xmlNumberPattern;
    }

    public void setXmlNumberPattern(String xmlNumberPattern) {
        this.xmlNumberPattern = xmlNumberPattern;
    }

    /**
     * Time-consuming operation, just for error diagnostics
     *
     * @return json string
     */
    @Override
    public String toString() {
        try {
            return ObjectHolder.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
