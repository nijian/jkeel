package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.config.ValidationConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ConfigItem<C extends Behavior<?>> {

    @XmlID
    @XmlAttribute
    private String id;

    @XmlElement
    protected String name;

    @XmlElement
    private Link link;

    private MappingConfig inMapping;

    private MappingConfig outMapping;

    private ValidationConfig validation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Link getLink() {
        return link;
    }

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

    protected abstract C getBehavior();
}
