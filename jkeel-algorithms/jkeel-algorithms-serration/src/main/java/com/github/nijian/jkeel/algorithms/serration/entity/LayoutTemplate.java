package com.github.nijian.jkeel.algorithms.serration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nijian.jkeel.algorithms.AlgorithmTemplate;
import com.github.nijian.jkeel.algorithms.serration.Const;
import groovy.lang.Closure;

import java.util.List;
import java.util.Map;

/**
 * Layout template
 *
 * @param <I> input type
 * @author nj
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LayoutTemplate<I> implements AlgorithmTemplate {

    /**
     * algorithm context global identifier
     */
    private String cid;

    /**
     * output fields
     */
    private String outputFields;

    /**
     * layout list
     */
    private List<Layout> layouts;

    public void exec(I input, Object calc, final Map<String, Closure> closureMap) {
        Closure closure = closureMap.get(Const.OUTPUT);
        if (closure != null) {
            closure.setDelegate(calc);
            closure.setResolveStrategy(Closure.DELEGATE_ONLY);
            closure.call(input);
        }
    }

    /**
     * Get algorithm context global identifier
     *
     * @return algorithm context global identifier
     */
    @Override
    public String getCid() {
        return cid;
    }

    /**
     * Set algorithm context global identifier
     *
     * @param cid algorithm context global identifier
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * Get output fields
     *
     * @return output fields
     */
    public String getOutputFields() {
        return outputFields;
    }

    /**
     * Set output fields
     *
     * @param outputFields output fields
     */
    public void setOutputFields(String outputFields) {
        this.outputFields = outputFields;
    }

    /**
     * Get layout list
     *
     * @return layout list
     */
    public List<Layout> getLayouts() {
        return layouts;
    }

    /**
     * Set layout list
     *
     * @param layouts layout list
     */
    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }
}
