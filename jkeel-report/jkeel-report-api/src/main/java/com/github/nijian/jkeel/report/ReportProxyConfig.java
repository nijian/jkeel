package com.github.nijian.jkeel.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ReportProxyConfig {

    /**
     * printer id
     */
    private String id;

    /**
     * implementation of printer
     */
    private String clzName;

    /**
     * is default or not
     */
    private Boolean isDefault = true;

    /**
     * properties of printer
     */
    @JsonRawValue
    private Object properties;

    /**
     * Get printer id
     *
     * @return printer id
     */
    public String getId() {
        return id;
    }

    /**
     * Set printer id
     *
     * @param id printer id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get printer implementation class name
     *
     * @return printer implementation class name
     */
    public String getClzName() {
        return clzName;
    }

    /**
     * Set printer implementation class name
     *
     * @param clzName printer implementation class name
     */
    public void setClzName(String clzName) {
        this.clzName = clzName;
    }

    /**
     * Is this printer default
     *
     * @return default flag
     */
    public Boolean isDefault() {
        return isDefault;
    }

    /**
     * Set printer as default printer
     *
     * @param aDefault default flag
     */
    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    /**
     * Get properties of printer
     *
     * @return string of properties (JSON format)
     */
    public String getProperties() {
        return properties == null ? null : properties.toString();
    }

    /**
     * Set properties of printer
     *
     * @param node JsonNode
     */
    public void setProperties(JsonNode node) {
        this.properties = node;
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
