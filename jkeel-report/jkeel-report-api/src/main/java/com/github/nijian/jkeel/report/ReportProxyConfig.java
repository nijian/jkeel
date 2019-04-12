package com.github.nijian.jkeel.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.nijian.jkeel.commons.ObjectHolder;

/**
 * ReportProxyConfig
 *
 * @author nj
 * @since 0.0.2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ReportProxyConfig {

    /**
     * ReportNonPoolProxy id
     */
    private String id;

    /**
     * ReportNonPoolProxy class name
     */
    private String clzName;

    /**
     * is default report proxy or not
     */
    private Boolean isDefault = true;

    /**
     * properties of ReportNonPoolProxy
     */
    @JsonRawValue
    private Object properties;

    /**
     * Get ReportNonPoolProxy id
     *
     * @return ReportNonPoolProxy id
     */
    public String getId() {
        return id;
    }

    /**
     * Set ReportNonPoolProxy id
     *
     * @param id ReportNonPoolProxy id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get ReportNonPoolProxy class name
     *
     * @return ReportNonPoolProxy class name
     */
    public String getClzName() {
        return clzName;
    }

    /**
     * Set ReportNonPoolProxy class name
     *
     * @param clzName ReportNonPoolProxy class name
     */
    public void setClzName(String clzName) {
        this.clzName = clzName;
    }

    /**
     * Is this ReportNonPoolProxy default
     *
     * @return default flag
     */
    public Boolean isDefault() {
        return isDefault;
    }

    /**
     * Set ReportNonPoolProxy as default
     *
     * @param aDefault default flag
     */
    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    /**
     * Get properties of ReportNonPoolProxy
     *
     * @return properties (JSON format)
     */
    public String getProperties() {
        return properties == null ? null : properties.toString();
    }

    /**
     * Set properties of ReportNonPoolProxy
     *
     * @param node JsonNode
     */
    public void setProperties(JsonNode node) {
        this.properties = node;
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
