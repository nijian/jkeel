package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.AlgorithmConfig;
import com.github.nijian.jkeel.concept.config.BehaviorType;
import com.github.nijian.jkeel.concept.config.Use;
import com.github.nijian.jkeel.concept.spi.DataAccessorFactoryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class Algorithm extends Behavior {

    private static Logger logger = LoggerFactory.getLogger(Algorithm.class);

    protected BehaviorProxy use(final BehaviorInput behaviorInput, final String ref) {

        ServiceContext ctx = behaviorInput.getContext();
        AlgorithmConfig algorithmConfig = getConfigItem(behaviorInput, AlgorithmConfig.class);

        List<Use> useList = algorithmConfig.getUseList();
        Use use = useList.stream().filter(item -> item.getRef().equals(ref)).findFirst().orElse(null);
        if (use == null) {
            logger.error("Behavior('{}') does not exists or is not allowed to use in Algorithm('{}')", ref, algorithmConfig.getId());
            throw new BehaviorException("Please check config and ask for use right");
        }

        BehaviorType behaviorType = use.getType();
        if (behaviorType.equals(BehaviorType.DA)) {
            String daName = Entry.parse(use.getRef()).getConceptName();
            Behavior behavior = DataAccessorFactoryProvider.getInstance().getData(daName);
            ConfigItem<?> dataAccessorConfig = ctx.getServicesConfig().getDataAccessorConfigMap().get(use.getRef());
            BehaviorProxy behaviorProxy = new BehaviorProxy(ctx, dataAccessorConfig, behavior);
            return behaviorProxy;
        } else {
            throw new BehaviorException("Only support DA for Algorithm use element");
        }
    }
}
