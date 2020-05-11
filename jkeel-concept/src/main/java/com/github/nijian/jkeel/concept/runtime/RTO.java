package com.github.nijian.jkeel.concept.runtime;

public class RTO {

    private final String id;

    private long startTime;

    private long executionTime;

    private RTO inMapping;

    private RTO validation;

    private RTO outMapping;

    private RTO child;

    public RTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public RTO getInMapping() {
        return inMapping;
    }

    public void setInMapping(RTO inMapping) {
        this.inMapping = inMapping;
    }

    public RTO getValidation() {
        return validation;
    }

    public void setValidation(RTO validation) {
        this.validation = validation;
    }

    public RTO getOutMapping() {
        return outMapping;
    }

    public void setOutMapping(RTO outMapping) {
        this.outMapping = outMapping;
    }

    public RTO getChild() {
        return child;
    }

    public void setChild(RTO child) {
        this.child = child;
    }

}
