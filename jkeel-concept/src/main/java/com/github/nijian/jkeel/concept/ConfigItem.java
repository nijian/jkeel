package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.MappingConfig;
import com.github.nijian.jkeel.concept.config.ValidationConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ConfigItem<C extends Behavior> {

    @XmlID
    @XmlAttribute
    private String id;

    @XmlElement
    private String name;

    @XmlAttribute
    private String iclass;

    @XmlAttribute
    private String rclass;

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
        if (name == null) {
            name = Entry.parse(id).getConceptName();
        }
        return name;
    }

    public String getIclass() {
        return iclass;
    }

    public void setIclass(String iclass) {
        this.iclass = iclass;
    }

    public String getRclass() {
        return rclass;
    }

    public void setRclass(String rclass) {
        this.rclass = rclass;
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

    public Class<?> getRclz() {
        try {
            return Class.forName(rclass);
        } catch (Exception e) {
            throw new BehaviorException("Can't find return class type", e);
        }
    }

    protected abstract C getBehavior();
}
