package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.algorithms.Template;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LayoutTemplate implements Template {

    private String name;
    private String outputNames;
    private List<Layout> layouts;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutputNames() {
        return outputNames;
    }

    public void setOutputNames(String outputNames) {
        this.outputNames = outputNames;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }
}
