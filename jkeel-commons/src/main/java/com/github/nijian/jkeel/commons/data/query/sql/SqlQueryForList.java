package com.github.nijian.jkeel.commons.data.query.sql;

import com.github.nijian.jkeel.commons.data.query.QueryForList;
import com.github.nijian.jkeel.commons.entity.query.Condition;
import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.concept.config.ConditionMeta;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SqlQueryForList extends QueryForList {

    @Override
    protected String generateQueryDSL(DataAccessorConfig dataAccessorConfig, Query query) {
        String sql = dataAccessorConfig.getSelect() + " " + dataAccessorConfig.getFrom();
        return generate(sql, query.getConditionList(), dataAccessorConfig.getConditionMetaList());
    }

    @Override
    protected String generateCountDSL(DataAccessorConfig dataAccessorConfig, Query query) {
        String sql = "select count(1) " + dataAccessorConfig.getFrom();
        return generate(sql, query.getConditionList(), dataAccessorConfig.getConditionMetaList());
    }

    private String generate(String sql, List<Condition<?>> conditionList, List<ConditionMeta> conditionMetaList) {
        if (conditionList == null) {
            return sql;
        }

        Map<String, Object> values = new HashMap<>();
        for (Condition<?> condition : conditionList) {
            StringBuffer sb = new StringBuffer();
            String key = condition.getName();
            for (ConditionMeta conditionMeta : conditionMetaList) {
                String name = conditionMeta.getName();
                if (name.equals(key)) {
                    String operator = conditionMeta.getOperator();
                    if(operator.equals("first")){
                        sb.append(" where ");
                    }else if(operator.equals("and")){
                        sb.append(" and ");
                    }else{
                        throw new RuntimeException("xvcaafdsa");
                    }
                }
            }
            values.put(key, sb.append(condition.toDSL()).toString());
        }


        return StringSubstitutor.replace(sql, values);
    }
}
