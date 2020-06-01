package com.github.nijian.jkeel.concept.config;


import javax.xml.bind.annotation.*;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

    @XmlAttribute
    private String id;

    @XmlElements(value = {
            @XmlElement(name = "actionRef",
                    type = ActionReference.class),
            @XmlElement(name = "algorithmRef",
                    type = AlgorithmReference.class),
            @XmlElement(name = "codeRef",
                    type = CodeReference.class),
            @XmlElement(name = "dataAccessorRef",
                    type = DataAccessorReference.class),
            @XmlElement(name = "mappingRef",
                    type = MappingReference.class),
            @XmlElement(name = "serviceRef",
                    type = ServiceReference.class),
            @XmlElement(name = "validationRef",
                    type = ValidationReference.class),
    })
    private BehaviorReference behaviorReference;

    @XmlAttribute
    private boolean var;

    @XmlAttribute
    private boolean out;

    @XmlElement
    private Param param;

    @XmlElement
    private ParamMap paramMap;

    @XmlElement
    private Link link;

    private Decision decision;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BehaviorReference getBehaviorReference() {
        return behaviorReference;
    }

    public void setBehaviorReference(BehaviorReference behaviorReference) {
        this.behaviorReference = behaviorReference;
    }

    public boolean isVar() {
        return var;
    }

    public void setVar(boolean var) {
        this.var = var;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public ParamMap getParamMap() {
        return paramMap;
    }

    public void setParamMap(ParamMap paramMap) {
        this.paramMap = paramMap;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }
}
