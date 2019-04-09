package com.github.nijian.jkeel.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ReportConfig {

    /**
     * Printer configuration list
     */
    private List<ReportProxyConfig> printerConfigs;

    /**
     * Get printer configuration list
     *
     * @return printer configuration list
     */
    public List<ReportProxyConfig> getPrinterConfigs() {
        return printerConfigs;
    }

    /**
     * Set printer configuration list
     *
     * @param printerConfigs printer configuration list
     */
    public void setPrinterConfigs(List<ReportProxyConfig> printerConfigs) {
        this.printerConfigs = printerConfigs;
    }

    /**
     * Time-consuming operation, just for error diagnostics
     *
     * @return object in json format
     */
    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
