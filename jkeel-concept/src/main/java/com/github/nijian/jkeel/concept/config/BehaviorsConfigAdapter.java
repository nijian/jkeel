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
    public BehaviorsConfig unmarshal(BehaviorsConfig v) {
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
                BehaviorReference behaviorReference = listener.getBehaviorReference();
                String myRef = behaviorReference.getRef();
                if (behaviorReference instanceof CodeReference) {
                    CodeConfig codeConfig = v.getCodeConfigMap().get(myRef);
                    behaviorReference.setBehaviorConfig(codeConfig);

                    Source source = codeConfig.getSource();
                    behaviorReference = source.getBehaviorReference();
                    myRef = behaviorReference.getRef();

                    if (behaviorReference instanceof DataAccessorReference) {
                        DataAccessorConfig dataAccessorConfig = v.getDataAccessorConfigMap().get(myRef);
                        behaviorReference.setBehaviorConfig(dataAccessorConfig);
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
    public BehaviorsConfig marshal(BehaviorsConfig v) {
        return null;
    }

    private void fixLink(Link link, BehaviorsConfig root) {
        BehaviorReference behaviorReference = link.getBehaviorReference();
        String ref = behaviorReference.getRef();
        if (behaviorReference instanceof ServiceReference) {
            fix0(link, root, root.getServiceConfigMap().get(ref));
        } else if (behaviorReference instanceof DataAccessorReference) {
            fix0(link, root, root.getDataAccessorConfigMap().get(ref));
        } else if (behaviorReference instanceof ActionReference) {
            fix0(link, root, root.getActionConfigMap().get(ref));
        } else if (behaviorReference instanceof AlgorithmReference) {
            AlgorithmConfig algorithmConfig = root.getAlgorithmConfigMap().get(ref);
            fix0(link, root, algorithmConfig);
            List<Use> useList = algorithmConfig.getUseList();
            for (Use use : useList) {
                behaviorReference = use.getBehaviorReference();
                ref = behaviorReference.getRef();
                if (behaviorReference instanceof DataAccessorReference) {
                    behaviorReference.setBehaviorConfig(root.getDataAccessorConfigMap().get(ref));
                } else {
                    throw new BehaviorException("wwwwwwait....");
                }
            }
        } else {
            throw new RuntimeException("ffafdsa");
        }

        Link nextLink = link.getBehaviorReference().getBehaviorConfig().getLink();
        if (nextLink != null) {
            fixLink(nextLink, root);
        }
    }

    private void fix0(Link link, BehaviorsConfig root, ConfigItem<?> configItem) {
        if (configItem == null) {
            throw new RuntimeException("xxx");
        }
        link.getBehaviorReference().setBehaviorConfig(configItem);
        Link nextLink = link.getLink();
        if (nextLink != null) {
            fixLink(nextLink, root);
        }
    }
}
