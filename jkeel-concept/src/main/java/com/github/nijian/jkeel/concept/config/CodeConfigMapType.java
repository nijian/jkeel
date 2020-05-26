package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class CodeConfigMapType {

    @XmlElement(name = "code")
    private List<CodeConfig> codeConfigList;

    public List<CodeConfig> getCodeConfigList() {
        return codeConfigList;
    }

    public void setCodeConfigList(List<CodeConfig> codeConfigList) {
        this.codeConfigList = codeConfigList;
    }
}
