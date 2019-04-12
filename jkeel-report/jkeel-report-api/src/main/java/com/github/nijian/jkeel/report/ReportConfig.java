package com.github.nijian.jkeel.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.commons.ObjectHolder;

import java.util.List;

/**
 * ReportConfig is used to configure a set of ReportNonPoolProxy
 *
 * @author nj
 * @since 0.0.2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ReportConfig {

    /**
     * ReportProxyConfig list
     */
    private List<ReportProxyConfig> reportProxyConfigs;

    /**
     * Get ReportProxyConfig list
     *
     * @return printer configuration list
     */
    public List<ReportProxyConfig> getReportProxyConfigs() {
        return reportProxyConfigs;
    }

    /**
     * Set ReportProxyConfig list
     *
     * @param reportProxyConfigs ReportProxyConfig list
     */
    public void setReportProxyConfigs(List<ReportProxyConfig> reportProxyConfigs) {
        this.reportProxyConfigs = reportProxyConfigs;
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
