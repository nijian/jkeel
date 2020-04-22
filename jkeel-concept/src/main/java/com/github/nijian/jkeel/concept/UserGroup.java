package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.ServicesConfig;

import java.util.List;

public abstract class UserGroup {

    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public abstract ServicesConfig getServicesConfig();
}
