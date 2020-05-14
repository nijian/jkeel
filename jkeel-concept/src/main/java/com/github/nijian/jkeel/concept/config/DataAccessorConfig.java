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

    private String select;

    private String from;

    private String where;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    @Override
    public DataAccessor getBehavior() {
        return DataAccessorFactoryProvider.getInstance().getData(getName());
    }
}
