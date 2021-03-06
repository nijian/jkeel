package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.paas.Tenant;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;

//might be cached
public class User {

    private SysCache sysCache = SysCache.getInstance();

    private String orgId;

    private Collection<Role> roles;

    public User(String orgId) {
        this.orgId = orgId;
    }

    public Org getOrg() {
        Org org = sysCache.getOrg(orgId);
        if (org == null) {
            //get org from cache first
            org = new Tenant(orgId);
            Config c = new Config() {
                @Override
                public String get(String term) {
                    try {
                        ClassLoader classLoader = getClass().getClassLoader();
                        File file = new File(classLoader.getResource("services.xml").getFile());
                        return FileUtils.readFileToString(file, "UTF-8");
                    } catch (Exception e) {
                        throw new RuntimeException("xxxxxxx");
                    }
                }
            };
            org.setConfig(c);
            sysCache.putOrg(orgId, org);
        }
        return org;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    // must be admin user
    public void saveConfig(Config newConfig){

        //publish event

        //service will be rollback and re-entry
    }
}
