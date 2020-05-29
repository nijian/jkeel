package com.github.nijian.jkeel.code;

import com.github.nijian.jkeel.code.entity.CodeItem;
import com.github.nijian.jkeel.concept.*;
import com.github.nijian.jkeel.concept.config.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleCodeList extends Code {

    @Override
    protected Object execute(BehaviorInput<Code, CodeConfig> behaviorInput) {

        Map<String, List<CodeItem>> codeList = new HashMap<>();

        ServiceContext ctx = behaviorInput.getContext();
        CodeConfig codeConfig = behaviorInput.getConfigItem();
        String key = (String) behaviorInput.getValue();

        Source source = codeConfig.getSource();
        BehaviorReference behaviorReference = source.getBehaviorReference();
        if (behaviorReference instanceof DataAccessorReference) {
            DataAccessorConfig dataAccessorConfig = (DataAccessorConfig) behaviorReference.getBehaviorConfig();
            DataAccessor dataAccessor = dataAccessorConfig.getBehavior();

            BehaviorInput<DataAccessor, DataAccessorConfig> newBehaviorInput = new BehaviorInput<>(ctx, dataAccessorConfig, key);
            List<CodeItem> singleCodeList = (List<CodeItem>) dataAccessor.apply(newBehaviorInput);
            codeList.put(key, singleCodeList);
        } else {
            throw new BehaviorException("non da is not support yet");
        }

        return codeList;
    }

}
