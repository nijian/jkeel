package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.paas.Tenant;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;

//might be cached
public class User<T extends Org> {

    private String orgId;

    private T org;

    private Collection<Role> roles;

    public User(String orgId) {
        this.orgId = orgId;

        //get org from cache first

        org = (T) new Tenant("abc");

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

    }

    public T getOrg() {
        return org;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
