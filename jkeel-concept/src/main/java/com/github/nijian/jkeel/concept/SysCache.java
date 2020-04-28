package com.github.nijian.jkeel.concept;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import java.net.URL;

public class SysCache {

    private static SysCache instance;

    private CacheManager myCacheManager;

    private SysCache() {
        URL myUrl = getClass().getResource("/ehcache3.xml");
        XmlConfiguration xmlConfig = new XmlConfiguration(myUrl);
        myCacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        myCacheManager.init();
    }

    public static SysCache getInstance() {
        if (instance == null) {
            instance = new SysCache();
        }
        return instance;
    }

    public Org getOrg(String orgId) {
        Org org = myCacheManager.getCache("ORG", String.class, Org.class).get(orgId);
        return org;
    }

    public void putOrg(String orgId, Org org) {
        myCacheManager.getCache("ORG", String.class, Org.class).put(orgId, org);
    }
}
