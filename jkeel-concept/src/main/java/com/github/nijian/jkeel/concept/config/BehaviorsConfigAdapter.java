package com.github.nijian.jkeel.concept.config;

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

    private void fixLink(Link link, BehaviorsConfig v) {
        String type = link.getType();
        String refid = link.getRefid();
        if (type.equals("service")) {

        } else if (type.equals("dataAccessor")) {
            DataAccessorConfig dataAccessorConfig = v.getDataAccessorConfigMap().get(refid);
            if (dataAccessorConfig == null) {
                throw new RuntimeException("xxx");
            }
            link.setBehaviorConfig(dataAccessorConfig);
        } else {
            throw new RuntimeException("ffafdsa");
        }

        Link nextLink = link.getBehaviorConfig().getLink();
        if (nextLink != null) {
            fixLink(nextLink, v);
        }


    }
}
