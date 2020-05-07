package com.github.nijian.jkeel.concept.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataAccessorConfigMapType {

    @XmlElement(name = "dataAccessor")
    private List<DataAccessorConfig> dataAccessorConfigList;

    public List<DataAccessorConfig> getDataAccessorConfigList() {
        return dataAccessorConfigList;
    }

    public void setDataAccessorConfigList(List<DataAccessorConfig> dataAccessorConfigList) {
        this.dataAccessorConfigList = dataAccessorConfigList;
    }
}
