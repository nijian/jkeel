package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.Code;
import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.*;

@XmlType(name = "code")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeConfig extends ConfigItem<Code> {

    @XmlID
    @XmlAttribute
    private String id;

    @XmlElement(name = "source")
    private Source source;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public Code getBehavior() {
        return null;
    }
}
