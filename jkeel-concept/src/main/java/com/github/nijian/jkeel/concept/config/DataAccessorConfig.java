package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.spi.DataFactoryProvider;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "dataAccessor")
public class DataAccessorConfig extends ConfigItem<DataAccessor> {

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public DataAccessor getConcept() {
        return DataFactoryProvider.getInstance().getData(getName());
    }

}
