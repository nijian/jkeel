package com.github.nijian.jkeel.concept;

public class ConceptInput<M extends Manager, T> {

    private M manager;

    private User user;

    private ConfigItem<?> configItem;

    private T value;

    public M getManager() {
        return manager;
    }

    public void setManager(M manager) {
        this.manager = manager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConfigItem<?> getConfigItem() {
        return configItem;
    }

    public void setConfigItem(ConfigItem<?> configItem) {
        this.configItem = configItem;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
