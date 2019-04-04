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

    /**
     * layout template
     */
    private final LayoutTemplate layoutTemplate;

    /**
     * layout instance list
     */
    private final List<LayoutInstance> layoutInstances = new ArrayList<>();

    /**
     * Constructor
     *
     * @param layoutTemplate layout template
     */
    LayoutTemplateInstance(LayoutTemplate layoutTemplate) {
        this.layoutTemplate = layoutTemplate;
    }

    /**
     * Get layout template
     *
     * @return layout template
     */
    public LayoutTemplate getLayoutTemplate() {
        return layoutTemplate;
    }

    /**
     * Get layout instance
     *
     * @return layout instance
     */
    public List<LayoutInstance> getLayoutInstances() {
        return layoutInstances;
    }

}
