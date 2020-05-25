package com.github.nijian.jkeel.commons.data.query.sql;

import com.github.nijian.jkeel.commons.data.query.QueryForList;
import com.github.nijian.jkeel.commons.entity.query.Condition;
import com.github.nijian.jkeel.commons.entity.query.QueryRequest;
import com.github.nijian.jkeel.commons.entity.query.QueryDSL;
import com.github.nijian.jkeel.concept.BehaviorException;
import com.github.nijian.jkeel.concept.config.ConditionMeta;
import com.github.nijian.jkeel.concept.config.ConditionOperator;
import com.github.nijian.jkeel.concept.config.DataAccessorConfig;
import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SqlQueryForList extends QueryForList {

    @Override
    protected QueryDSL generateQueryDSL(DataAccessorConfig dataAccessorConfig, QueryRequest queryRequest) {
        String sql = dataAccessorConfig.getSelect() + " " + dataAccessorConfig.getFrom();
        return generate(sql, queryRequest.getConditionList(), dataAccessorConfig.getConditionMetaList());
    }

    @Override
    protected QueryDSL generateCountDSL(DataAccessorConfig dataAccessorConfig, QueryRequest queryRequest) {
        String sql = "select count(1) " + dataAccessorConfig.getFrom();
        return generate(sql, queryRequest.getConditionList(), dataAccessorConfig.getConditionMetaList());
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
            String key = condition.getName();
            String alias = null;
            StringBuffer sb = new StringBuffer();
            boolean matched = false;
            for (ConditionMeta conditionMeta : conditionMetaList) {
                String name = conditionMeta.getName();
                if (name.equals(key)) {
                    ConditionOperator operator = conditionMeta.getOperator();
                    if (operator.equals(ConditionOperator.FIRST)) {
                        sb.append(" where ");
                    } else if (operator.equals(ConditionOperator.AND)) {
                        sb.append(" and ");
                    } else if (operator.equals(ConditionOperator.OR)) {
                        sb.append(" or ");
                    } else {
                        throw new RuntimeException("xvcaafdsa");
                    }
                    matched = true;
                    alias = conditionMeta.getAlias();
                    break;
                }
            }
            if (!matched) {
                throw new BehaviorException("Please check condition config");
            }
            args.add(condition.getValue());
            values.put(key, sb.append(condition.toDSL(alias)).toString());
        }
        queryDSL.setDsl(StringSubstitutor.replace(sql, values));
        queryDSL.setArgs(args);

        System.out.println(queryDSL.getDsl());
        return queryDSL;
    }
}
