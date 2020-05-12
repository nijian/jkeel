package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.spi.DataAccessorFactoryProvider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType(name = "dataAccessor")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAccessorConfig extends ConfigItem<DataAccessor> {

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public DataAccessor getBehavior() {
        return DataAccessorFactoryProvider.getInstance().getData(getName());
    }
}
