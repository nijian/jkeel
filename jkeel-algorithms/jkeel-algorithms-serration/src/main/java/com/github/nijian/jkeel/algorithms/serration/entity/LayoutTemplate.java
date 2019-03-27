package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.algorithms.AlgorithmTemplate;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LayoutTemplate implements AlgorithmTemplate {

    private String cid;
    private String outputFields;
    private List<Layout> layouts;

    @Override
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getOutputFields() {
        return outputFields;
    }

    public void setOutputFields(String outputFields) {
        this.outputFields = outputFields;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }
}
