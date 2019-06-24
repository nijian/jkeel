package com.github.nijian.jkeel.cache;

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
 * </ul>
 *
 * @author nj
 * @since 0.0.7
 */
public class CacheService {

    private static Logger logger = LoggerFactory.getLogger(CacheService.class);

    public Cache<String, ?> getCache(final String cacheId, final Properties env) {
        return getCache(cacheId, null, (String) null, env);
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
            String cpn = cachingProviderName;
            if (cpn == null) {
                cpn = env == null ? null : env.getProperty(Const.CACHING_PROVIDER_NAME_KEY);
                if (cpn == null) {
                    cpn = Const.CACHING_PROVIDER_NAME;
                }
            }
            CachingProvider cachingProvider = Caching.getCachingProvider(cpn);
            logger.info("Cache Provider:{}", cpn);

            URI cu = configURI;
            if (cu == null) {
                String cacheConfigFileName = env == null ? null : env.getProperty(Const.CACHING_CONFIG_FILE_NAME_KEY);
                if (cacheConfigFileName == null) {
                    cacheConfigFileName = Const.CACHING_CONFIG_FILE_NAME;
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
