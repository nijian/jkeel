package com.github.nijian.jkeel.algorithms;

public class TemplateAlgorithmContext<T extends AlgorithmTemplate, C extends AlgorithmConfig> extends AlgorithmContext {

    private T template;

    public TemplateAlgorithmContext(String cid, T template, C config) {
        super(cid, config);
        this.template = template;
    }

    public T getTemplate() {
        return template;
    }
}
