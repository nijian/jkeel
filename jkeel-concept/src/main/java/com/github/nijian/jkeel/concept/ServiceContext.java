package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.BehaviorsConfig;
import com.github.nijian.jkeel.concept.config.Link;
import com.github.nijian.jkeel.concept.config.ServiceConfig;
import com.github.nijian.jkeel.concept.runtime.BehaviorListener;
import com.github.nijian.jkeel.concept.runtime.RTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public final class ServiceContext implements BehaviorListener {

    private final Manager manager;

    private final User user;

    private final String serviceId;

    private final Object originalValue;

    private final Transaction transaction;

    private final Map<String, Object> vars;

    private final Map<String, Object> out;

    private final Stack<Link> linkStack;

    private final Stack<RTO> rtoStack;

    private final RTO rootRTO;

    private RTO currentRTO;

    private boolean useJPA;

    private boolean needSyncData;

    private ServiceContext(Manager manager, User user, String serviceId, Object originalValue) {
        this.manager = manager;
        this.user = user;
        this.originalValue = originalValue;
        this.serviceId = serviceId;

        this.transaction = new Transaction() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
        vars = new HashMap<>();
        out = new HashMap<>();
        linkStack = new Stack<>();
        rtoStack = new Stack<>();
        this.rootRTO = new RTO("root");
        this.currentRTO = rootRTO;
    }

    public static ServiceContext newInstance(Manager manager, User user, String serviceEntryName, Object originalValue) {
        return new ServiceContext(manager, user, serviceEntryName, originalValue);
    }

    public Manager getManager() {
        return manager;
    }

    public User getUser() {
        return user;
    }

    public String getServiceId() {
        return serviceId;
    }

    public Object getOriginalValue() {
        return originalValue;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Org getOrg() {
        return user.getOrg();
    }

    public Map<String, Object> getVars() {
        return vars;
    }

    public Map<String, Object> getOut() {
        return out;
    }

    public Stack<Link> getLinkStack() {
        return linkStack;
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

    public ServiceConfig getServiceConfig() {
        ServiceConfig serviceConfig = getServicesConfig().getServiceConfigMap().get(serviceId);
        return serviceConfig;
    }

    public boolean isUseJPA() {
        return useJPA;
    }

    public void setUseJPA(boolean useJPA) {
        this.useJPA = useJPA;
    }

    public boolean isNeedSyncData() {
        return needSyncData;
    }

    public void setNeedSyncData(boolean needSyncData) {
        this.needSyncData = needSyncData;
    }

    public String rtInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("**************************************\t\n");
        appendInfo(sb, rootRTO.getChild(), 0);
        sb.append("**************************************\t\n");
        return sb.toString();
    }

    private void appendInfo(StringBuffer sb, RTO currentRTO, int level) {
        StringBuffer preSb = new StringBuffer();
        for (int i = 0; i < level; i++) {
            preSb.append("-> ");
        }
        sb.append(preSb).append(currentRTO.getId()).append(":").append(currentRTO.getExecutionTime()).append("ms").append("\t\n");
        RTO inMappingRTO = currentRTO.getInMapping();
        if (inMappingRTO != null) {
            sb.append(preSb).append("-> ").append(inMappingRTO.getId()).append(":").append(inMappingRTO.getExecutionTime()).append("ms").append("\t\n");
        }

        RTO childRTO = currentRTO.getChild();
        if (childRTO != null) {
            appendInfo(sb, childRTO, ++level);
        }
    }

    @Override
    public void onStart(ConfigItem<?> configItem) {
        RTO rto = new RTO(configItem.getId());
        rtoStack.push(rto);
        currentRTO.setChild(rto);
        currentRTO = currentRTO.getChild();
        currentRTO.setStartTime(System.currentTimeMillis());
    }

    @Override
    public void onEnd(ConfigItem<?> configItem) {
        try {
            RTO rto = rtoStack.pop();
            rto.setExecutionTime(System.currentTimeMillis() - rto.getStartTime());
        } catch (Exception e) {
            System.out.println("xxvsafdsa");
        }
    }

    public Map<String, Object> run() {

        ServiceConfig serviceConfig = getServiceConfig();
        Service service = serviceConfig.getBehavior();
        BehaviorInput serviceInput = new BehaviorInput(this, serviceConfig, originalValue);
        service.apply(serviceInput);

        return out;
    }
}
