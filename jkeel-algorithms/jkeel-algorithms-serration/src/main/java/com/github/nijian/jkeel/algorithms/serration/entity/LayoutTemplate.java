package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.algorithms.AlgorithmTemplate;
import com.github.nijian.jkeel.algorithms.serration.Const;
import groovy.lang.Closure;

import javax.cache.Cache;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LayoutTemplate<I> implements AlgorithmTemplate {

    private String cid;
    private String outputFields;
    private List<Layout> layouts;

    public void exec(I input, LayoutTemplateInstance layoutTemplateInstance, Cache<String, Closure> closureCache) {
        Closure closure = closureCache.get(Const.OUTPUT);
        if (closure != null) {
            closure.setDelegate(layoutTemplateInstance);
            closure.setResolveStrategy(Closure.DELEGATE_ONLY);
            closure.call(input);
        }
    }

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
