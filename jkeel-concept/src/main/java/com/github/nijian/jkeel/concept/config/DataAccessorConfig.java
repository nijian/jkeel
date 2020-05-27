package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;
import com.github.nijian.jkeel.concept.DataAccessor;
import com.github.nijian.jkeel.concept.spi.DataAccessorFactoryProvider;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlType(name = "dataAccessor")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAccessorConfig extends ConfigItem<DataAccessor> {

    @XmlID
    @XmlAttribute
    private String id;

    private String query;

    private String select;

    private String from;

    private String where;

    @XmlElement(name = "condition")
    private List<ConditionMeta> conditionMetaList;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<ConditionMeta> getConditionMetaList() {
        return conditionMetaList;
    }

    public void setConditionMetaList(List<ConditionMeta> conditionMetaList) {
        this.conditionMetaList = conditionMetaList;
    }

    @Override
    public DataAccessor getBehavior() {
        return DataAccessorFactoryProvider.getInstance().getData(getName());
    }
}
