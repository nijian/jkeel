package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.ServiceConfig;
import com.github.nijian.jkeel.concept.config.BehaviorsConfig;
import com.github.nijian.jkeel.concept.runtime.RTO;

public final class ServiceContext<M extends Manager> {

    private final M manager;

    private final User user;

    private final RTO rootRTO;

    private RTO currentRTO;

    public ServiceContext(M manager, User user) {
        this.manager = manager;
        this.user = user;
        this.rootRTO = new RTO();
        this.currentRTO = rootRTO;
    }

    public M getManager() {
        return manager;
    }

    public User getUser() {
        return user;
    }

    public Org getOrg() {
        return user.getOrg();
    }

    public RTO getRootRTO() {
        return rootRTO;
    }

    public RTO getCurrentRTO() {
        return currentRTO;
    }

    public void setCurrentRTO(RTO currentRTO) {
        this.currentRTO = currentRTO;
    }

    public BehaviorsConfig getServicesConfig() {
        return getOrg().getConfig().getBehaviorsConfig();
    }

    public ServiceConfig getServiceConfig(String serviceEntryName) {
        ServiceConfig serviceConfig = getServicesConfig().getServiceConfigMap().get(serviceEntryName);
        return serviceConfig;
    }

    public String rtInfo() {
        StringBuffer sb = new StringBuffer();
        appendInfo(sb, rootRTO, 0);
        return sb.toString();
    }

    private void appendInfo(StringBuffer sb, RTO currentRTO, int level) {
        StringBuffer preSb = new StringBuffer();
        for (int i = 0; i < level; i++) {
            preSb.append("--");
        }
        sb.append(preSb).append(currentRTO.getId()).append(":").append(currentRTO.getExecutionTime()).append("ms").append("\t\n");
        RTO inMappingRTO = currentRTO.getInMapping();
        if (inMappingRTO != null) {
            sb.append(preSb).append("--").append(inMappingRTO.getId()).append(":").append(inMappingRTO.getExecutionTime()).append("ms").append("\t\n");
        }

        RTO childRTO = currentRTO.getChild();
        if (childRTO != null) {
            appendInfo(sb, childRTO, ++level);
        }
    }
}
