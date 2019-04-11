package com.github.nijian.jkeel.report.jasper;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.commons.ObjectHolder;
import com.github.nijian.jkeel.report.ReportExtProperties;

/**
 * JasperReportProperties
 *
 * @author nj
 * @since 0.0.2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class JasperReportProperties extends ReportExtProperties {

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
