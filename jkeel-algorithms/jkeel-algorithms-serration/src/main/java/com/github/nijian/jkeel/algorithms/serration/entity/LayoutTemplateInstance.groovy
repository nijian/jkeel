package com.github.nijian.jkeel.algorithms.serration.entity

import com.github.nijian.jkeel.algorithms.serration.MixinFuncs

/**
 * LayoutTemplateInstance
 *
 * @author nj
 * @since 0.0.1
 */
final class LayoutTemplateInstance implements MixinFuncs {

    Context<?> context
    LayoutTemplate layoutTemplate
    List<LayoutInstance> layoutInstances = new ArrayList<LayoutInstance>()

    LayoutTemplateInstance(Context<?> context, LayoutTemplate layoutTemplate) {
        this.context = context
        this.layoutTemplate = layoutTemplate
    }
}
