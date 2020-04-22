package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.Link;

import javax.xml.bind.annotation.XmlElement;

public abstract class ConfigItem<C extends ManagedConcept> {

    protected String entryName;

    private String className;

    protected String name;

    private Link link;

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

    public abstract C getConcept();
}
