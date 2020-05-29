package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.*;
import com.github.nijian.jkeel.concept.spi.DataAccessorFactoryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class Algorithm extends Behavior<Algorithm, AlgorithmConfig> {

    private static Logger logger = LoggerFactory.getLogger(Algorithm.class);

    protected BehaviorProxy use(final BehaviorInput<Algorithm, AlgorithmConfig> behaviorInput, final String ref) {

        ServiceContext ctx = behaviorInput.getContext();
        AlgorithmConfig algorithmConfig = behaviorInput.getConfigItem();

        List<Use> useList = algorithmConfig.getUseList();
        Use use = useList.stream().filter(item -> item.getBehaviorReference().getRef().equals(ref)).findFirst().orElse(null);
        if (use == null) {
            logger.error("Behavior('{}') does not exists or is not allowed to use in Algorithm('{}')", ref, algorithmConfig.getId());
            throw new BehaviorException("Please check config and ask for use right");
        }

        BehaviorReference behaviorReference = use.getBehaviorReference();
        String myRef = behaviorReference.getRef();
        String behaviorName = Entry.parse(myRef).getBehaviorName();

        if (behaviorReference instanceof DataAccessorReference) {
            DataAccessor<?> dataAccessor = DataAccessorFactoryProvider.getInstance().getData(behaviorName);
            DataAccessorConfig dataAccessorConfig = ctx.getServicesConfig().getDataAccessorConfigMap().get(myRef);
            return new BehaviorProxy(ctx, dataAccessorConfig, dataAccessor);
        } else {
            throw new BehaviorException("Only support DA for Algorithm use element");
        }
    }
}
