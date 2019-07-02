package com.github.nijian.jkeel.cache;

import com.github.nijian.jkeel.commons.Const;
import org.apache.tamaya.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * Simple wrapper for javax.cache lib. The benefits are:
 * <ul>
 * <li>Cache name/id is managed</li>
 * <li>Cache creation is managed</li>
 * <li>Introduce owner concept for multi tenant</li>
 * </ul>
 *
 * @author nj
 * @since 0.0.7
 */
public final class CacheService {

    private static Logger logger = LoggerFactory.getLogger(CacheService.class);

    public <K, V> Cache<K, V> getCache(String cacheId, Configuration config) {
        return getCache(Const.PUBLIC, cacheId, config);
    }

    public <K, V> Cache<K, V> getCache(String ownerId, final String cacheId, final Configuration config) {
        String finalCacheId = ownerId + "_" + cacheId;

        String cpn = getCachingProviderName(config);
        CachingProvider cachingProvider = Caching.getCachingProvider(cpn);

        return getCache(ownerId, cacheId, config);
    }

    private String getCachingProviderName(Configuration config) {
        if (config != null) {
            String cpn = config.get(JkeelCacheConst.CACHING_PROVIDER_NAME_KEY);
            if (cpn != null) {
                return cpn;
            }
        }
        return JkeelCacheConst.CACHING_PROVIDER_NAME;
    }

    public Cache<String, ?> getCache(final String cacheId, final URI configURI, final Properties env) {
        return getCache(cacheId, null, configURI, env);
    }

    public Cache<String, ?> getCache(final String cacheId, final String cachingProviderName, final String configClassPath,
                                     final Properties env) {
        return null;
    }

    public Cache<String, ?> getCache(final String cacheId, final String cachingProviderName, final URI configURI,
                                     final Properties env) {

        try {

            CachingProvider cachingProvider = Caching.getCachingProvider(cachingProviderName);
            logger.info("Cache Provider:{}", cachingProviderName);

            URI cu = configURI;
            if (cu == null) {
                String cacheConfigFileName = env == null ? null : env.getProperty(JkeelCacheConst.CACHING_CONFIG_FILE_NAME_KEY);
                if (cacheConfigFileName == null) {
                    cacheConfigFileName = JkeelCacheConst.CACHING_CONFIG_FILE_NAME;
                }
                cu = getClass().getResource(cacheConfigFileName).toURI();
            }
            CacheManager cacheManager = cachingProvider.getCacheManager(cu, getClass().getClassLoader());
            MutableConfiguration<String, ?> configuration = new MutableConfiguration().setStoreByValue(false);

            Cache<String, ?> cache = cacheManager.createCache(cacheId, configuration);
            logger.info("Cache is ready for {}", cacheId);

            return cache;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
