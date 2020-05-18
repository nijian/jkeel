package com.github.nijian.jkeel.commons.data.query.sql;

import com.github.nijian.jkeel.commons.data.query.QueryForList;
import com.github.nijian.jkeel.commons.entity.query.Condition;
import com.github.nijian.jkeel.commons.entity.query.Query;
import com.github.nijian.jkeel.commons.entity.query.QueryDSL;
import com.github.nijian.jkeel.concept.config.ConditionMeta;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;
import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SqlQueryForList extends QueryForList {

    @Override
    protected QueryDSL generateQueryDSL(DataAccessorConfig dataAccessorConfig, Query query) {
        String sql = dataAccessorConfig.getSelect() + " " + dataAccessorConfig.getFrom();
        return generate(sql, query.getConditionList(), dataAccessorConfig.getConditionMetaList());
    }

    @Override
    protected QueryDSL generateCountDSL(DataAccessorConfig dataAccessorConfig, Query query) {
        String sql = "select count(1) " + dataAccessorConfig.getFrom();
        return generate(sql, query.getConditionList(), dataAccessorConfig.getConditionMetaList());
    }

    private QueryDSL generate(String sql, List<Condition<?>> conditionList, List<ConditionMeta> conditionMetaList) {
        QueryDSL queryDSL = new QueryDSL();
        if (conditionList == null) {
            queryDSL.setDsl(sql);
            return queryDSL;
        }

        List<Object> args = new ArrayList<>();
        Map<String, Object> values = new HashMap<>();
        for (Condition<?> condition : conditionList) {
            StringBuffer sb = new StringBuffer();
            String key = condition.getName();
            for (ConditionMeta conditionMeta : conditionMetaList) {
                String name = conditionMeta.getName();
                if (name.equals(key)) {
                    String operator = conditionMeta.getOperator();
                    if (operator.equals("first")) {
                        sb.append(" where ");
                    } else if (operator.equals("and")) {
                        sb.append(" and ");
                    } else {
                        throw new RuntimeException("xvcaafdsa");
                    }
                }
            }
            args.add(condition.getValue());
            values.put(key, sb.append(condition.toDSL()).toString());
        }
        queryDSL.setDsl(StringSubstitutor.replace(sql, values));
        queryDSL.setArgs(args);
        return queryDSL;
    }
}
