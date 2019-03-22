package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.Config

import javax.cache.Cache
import javax.cache.CacheManager
import javax.cache.Caching
import javax.cache.spi.CachingProvider

class CalcConfig implements Config, MixinFuncs {

    CacheManager cacheManager
    Cache<String, Closure> closureCache
    String cid
    boolean lock = false

    def cid(String cid) {
        this.cid = cid
    }

    def call(Closure closure) {
        closure.setDelegate(this)
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.call()
    }

    def Convert(Closure closure) {
        getCache().put(cid + Const.CONVERT, closure)
    }

    def ItemGroupCount(Closure closure) {
        getCache().put(cid + Const.ITEM_GROUP_COUNT, closure)
    }

    def LayoutCount(Closure closure) {
        getCache().put(cid + Const.LAYOUT_COUNT, closure)
    }

    def strategy(String name, Closure closure) {
        getCache().put(cid + name, closure)
    }

    def formula(String name, Closure closure) {
        getCache().put(cid + name, closure)
    }

    @Override
    <K, V> Cache<K, V> getCache() {
        //get default cache
        if (closureCache == null) {
            lock = true
            synchronized (lock) {
                if (closureCache == null) {
                    closureCache = getCacheManager().getCache('closure-cache', String.class, Closure.class)
                }
            }
            lock = false
        }
        return closureCache
    }

    @Override
    <K, V> Cache<K, V> getCache(String name, Class<K> keyClz, Class<V> valueClz) {
        if (closureCache == null) {
            lock = true
            synchronized (lock) {
                if (closureCache == null) {
                    closureCache = getCacheManager().getCache(name, K.class, V.class)
                }
            }
            lock = false
        }
        return closureCache
    }

    CacheManager getCacheManager() {
        if (cacheManager == null) {
            synchronized (lock) {
                lock = true
                if (cacheManager == null) {
                    CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider")
                    cacheManager = cachingProvider.getCacheManager(
                            getClass().getResource("/serration-ehcache.xml").toURI(),
                            getClass().getClassLoader())
                }
                lock = false
            }

        }
        return cacheManager
    }
}
