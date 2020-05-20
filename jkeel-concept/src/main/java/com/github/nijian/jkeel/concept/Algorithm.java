package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.AlgorithmConfig;
import com.github.nijian.jkeel.concept.config.BehaviorType;
import com.github.nijian.jkeel.concept.config.Use;
import com.github.nijian.jkeel.concept.spi.DataAccessorFactoryProvider;

import java.util.List;

public abstract class Algorithm extends Behavior {

    protected BehaviorProxy use(BehaviorInput behaviorInput, String ref) {

        ServiceContext<?> ctx = behaviorInput.getContext();
        AlgorithmConfig algorithmConfig = getConfigItem(behaviorInput, AlgorithmConfig.class);

        List<Use> useList = algorithmConfig.getUseList();
        Use use = useList.stream().filter(item -> item.getRef().equals(ref)).findFirst().get();
        if (use == null) {
            throw new BehaviorException("used an unavailable behavior");
        }

        BehaviorType behaviorType = use.getType();
        if (behaviorType.equals(BehaviorType.DA)) {
            String daName = Entry.parse(use.getRef()).getConceptName();
            Behavior behavior = DataAccessorFactoryProvider.getInstance().getData(daName);
            ConfigItem<?> dataAccessorConfig = ctx.getServicesConfig().getDataAccessorConfigMap().get(use.getRef());
            BehaviorProxy behaviorProxy = new BehaviorProxy(ctx, dataAccessorConfig, behavior);
            return behaviorProxy;
        } else {
            throw new BehaviorException("Only support dataaccessor type here");
        }
    }
}
