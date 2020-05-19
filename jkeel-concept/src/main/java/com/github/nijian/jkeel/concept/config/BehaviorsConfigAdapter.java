package com.github.nijian.jkeel.concept.config;

import com.github.nijian.jkeel.concept.ConfigItem;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Iterator;
import java.util.Map;

public class BehaviorsConfigAdapter extends XmlAdapter<BehaviorsConfig, BehaviorsConfig> {

    @Override
    public BehaviorsConfig unmarshal(BehaviorsConfig v) throws Exception {
        Map<String, ServiceConfig> serviceMap = v.getServiceConfigMap();
        Iterator<String> serviceIdIter = serviceMap.keySet().iterator();
        while (serviceIdIter.hasNext()) {
            ServiceConfig serviceConfig = serviceMap.get(serviceIdIter.next());
            fixLink(serviceConfig.getLink(), v);
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
            fix0(link, root, root.getAlgorithmConfigMap().get(ref));
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
