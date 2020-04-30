package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.config.ValidationConfig;

import javax.xml.bind.annotation.XmlElement;

public abstract class ConfigItem<C extends Behavior<?>> {

    protected String entryName;

    private String className;

    protected String name;

    private Link link;

    private MappingConfig inMapping;

    private MappingConfig outMapping;

    private ValidationConfig validation;

    public String getEntryName() {
        return entryName;
    }

    @XmlElement
    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Link getLink() {
        return link;
    }

    @XmlElement
    public void setLink(Link link) {
        this.link = link;
    }

    public MappingConfig getInMapping() {
        return inMapping;
    }

    public void setInMapping(MappingConfig inMapping) {
        this.inMapping = inMapping;
    }

    public MappingConfig getOutMapping() {
        return outMapping;
    }

    public void setOutMapping(MappingConfig outMapping) {
        this.outMapping = outMapping;
    }

    public ValidationConfig getValidation() {
        return validation;
    }

    public void setValidation(ValidationConfig validation) {
        this.validation = validation;
    }

    protected abstract C getConcept();
}
