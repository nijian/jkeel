package com.github.nijian.jkeel.report;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Export parameters
 *
 * @author nj
 * @since 0.0.2
 */
public abstract class ExportParams {

    public final static String RP_DATA_TYPE_INLINE = "inline";

    public final static String RP_DATA_TYPE_URI = "uri";

    private String exportFileURI;

    /**
     * uri
     * inline
     */
    private String rpDataSource;

    @JsonRawValue
    private Object rpData;

    public String getExportFileURI() {
        return exportFileURI;
    }

    public void setExportFileURI(String exportFileURI) {
        this.exportFileURI = exportFileURI;
    }

    public String getRpDataSource() {
        return rpDataSource;
    }

    public void setRpDataSource(String rpDataSource) {
        this.rpDataSource = rpDataSource;
    }

    public String getRpData() {
        return rpData == null ? null : rpData.toString();
    }

    public void setRpData(JsonNode rpData) {
        this.rpData = rpData;
    }
}
