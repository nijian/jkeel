package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.BehaviorException;
import com.github.nijian.jkeel.concept.ConfigItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BehaviorsConfigAdapter extends XmlAdapter<BehaviorsConfig, BehaviorsConfig> {

    private static Logger logger = LoggerFactory.getLogger(BehaviorsConfigAdapter.class);

    @Override
    public BehaviorsConfig unmarshal(BehaviorsConfig v) throws Exception {
        Map<String, ServiceConfig> serviceMap = v.getServiceConfigMap();
        Iterator<String> serviceIdIter = serviceMap.keySet().iterator();
        while (serviceIdIter.hasNext()) {
            ServiceConfig serviceConfig = serviceMap.get(serviceIdIter.next());
            Link link = serviceConfig.getLink();
            if (link == null) {
                logger.error("Missing Link config in service('{}')", serviceConfig.getId());
                throw new BehaviorException("Missing Link config");
            }
            fixLink(serviceConfig.getLink(), v);

            List<Listener> listenerList = serviceConfig.getListenerList();
            if (listenerList == null || listenerList.size() == 0) {
                continue;
            }

            for (Listener listener : listenerList) {
                BehaviorType type = listener.getType();
                String ref = listener.getRef();
                if (type.equals(BehaviorType.CO)) {
                    CodeConfig codeConfig = v.getCodeConfigMap().get(ref);
                    listener.setBehaviorConfig(codeConfig);

                    Source source = codeConfig.getSource();
                    type = source.getType();
                    ref = source.getRef();
                    if (type.equals(BehaviorType.DA)) {
                        DataAccessorConfig dataAccessorConfig = v.getDataAccessorConfigMap().get(ref);
                        source.setBehaviorConfig(dataAccessorConfig);
                        if (dataAccessorConfig.getLink() != null) {
                            fixLink(dataAccessorConfig.getLink(), v);
                        }
                    }
                }
            }
        }
        return v;
    }

    @Override
    public BehaviorsConfig marshal(BehaviorsConfig v) throws Exception {
        return null;
    }

    private void fixLink(Link link, BehaviorsConfig root) {
        BehaviorType type = link.getType();
        String ref = link.getRef();
        if (type.equals(BehaviorType.SE)) {
            fix0(link, root, root.getServiceConfigMap().get(ref));
        } else if (type.equals(BehaviorType.DA)) {
            fix0(link, root, root.getDataAccessorConfigMap().get(ref));
        } else if (type.equals(BehaviorType.AC)) {
            fix0(link, root, root.getActionConfigMap().get(ref));
        } else if (type.equals(BehaviorType.AL)) {
            AlgorithmConfig algorithmConfig = root.getAlgorithmConfigMap().get(ref);
            fix0(link, root, algorithmConfig);
            List<Use> useList = algorithmConfig.getUseList();
            for (Use use : useList) {
                use.setBehaviorConfig(root.getDataAccessorConfigMap().get(use.getRef()));
            }
        } else {
            throw new RuntimeException("ffafdsa");
        }

        Link nextLink = link.getBehaviorConfig().getLink();
        if (nextLink != null) {
            fixLink(nextLink, root);
        }
    }

    private void fix0(Link link, BehaviorsConfig root, ConfigItem<?> configItem) {
        if (configItem == null) {
            throw new RuntimeException("xxx");
        }
        link.setBehaviorConfig(configItem);
        Link nextLink = link.getLink();
        if (nextLink != null) {
            fixLink(nextLink, root);
        }
    }
}
