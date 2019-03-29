package com.github.nijian.jkeel.algorithms;

/**
 * TemplateAlgorithmContext
 *
 * @param <T> algorithm template
 * @param <C> algorithm config
 * @author nj
 * @see AlgorithmContext
 * @see AlgorithmTemplate
 * @see AlgorithmConfig
 * @since 0.0.1
 */
public final class TemplateAlgorithmContext<T extends AlgorithmTemplate, C extends AlgorithmConfig> extends AlgorithmContext {

    /**
     * AlgorithmTemplate
     */
    private T template;

    /**
     * Construction
     *
     * @param cid      algorithm context global identifier
     * @param template algorithm template
     * @param config   algorith config
     */
    public TemplateAlgorithmContext(String cid, T template, C config) {
        super(cid, config);
        this.template = template;
    }

    /**
     * Get algorithm template
     *
     * @return algorithm template
     */
    public T getTemplate() {
        return template;
    }
}
