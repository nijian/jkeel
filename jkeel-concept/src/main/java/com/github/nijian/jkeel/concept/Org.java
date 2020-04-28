package com.github.nijian.jkeel.concept;

import java.util.List;

//should be cached
public class Org {

    protected String id;

    protected Config config;

    protected List<Role> roleList;

    public Org(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

}
