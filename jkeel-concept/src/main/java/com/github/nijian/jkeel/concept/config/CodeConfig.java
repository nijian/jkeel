package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.Code;
import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "code")
public class CodeConfig extends ConfigItem<Code> {

    @XmlElement(name = "source")
    private Source source;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    protected Code getBehavior() {
        return null;
    }
}
