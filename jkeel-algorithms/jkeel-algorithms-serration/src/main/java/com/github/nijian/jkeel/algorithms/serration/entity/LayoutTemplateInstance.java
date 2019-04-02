package com.github.nijian.jkeel.algorithms.serration.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * LayoutTemplateInstance
 *
 * @author nj
 * @since 0.0.1
 */
public class LayoutTemplateInstance {

    private LayoutTemplate layoutTemplate;
    private List<LayoutInstance> layoutInstances = new ArrayList<>();

    LayoutTemplateInstance(LayoutTemplate layoutTemplate) {
        this.layoutTemplate = layoutTemplate;
    }

    public LayoutTemplate getLayoutTemplate() {
        return layoutTemplate;
    }

    public void setLayoutTemplate(LayoutTemplate layoutTemplate) {
        this.layoutTemplate = layoutTemplate;
    }

    public List<LayoutInstance> getLayoutInstances() {
        return layoutInstances;
    }

    public void setLayoutInstances(List<LayoutInstance> layoutInstances) {
        this.layoutInstances = layoutInstances;
    }
}
