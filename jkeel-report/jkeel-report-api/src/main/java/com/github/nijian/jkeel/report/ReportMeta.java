package com.github.nijian.jkeel.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.commons.ObjectHolder;

/**
 * ReportMeta provides generated report info.
 *
 * @author nj
 * @since 0.0.2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportMeta {

    private String protocol;

    private String uri;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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
