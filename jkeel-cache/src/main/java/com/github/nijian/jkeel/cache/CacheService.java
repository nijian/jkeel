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

    public <K, V> Cache<K, V> getCache(String ownerId, String cacheId, Configuration config) {

        String finalCacheId = JkeelCacheConst.CACHING_ID_PREFIX + "_" + ownerId + "_" + cacheId;
        logger.info("Cache id : {}", finalCacheId);

        String cpn = getCachingProviderName(config);
        CachingProvider cachingProvider = Caching.getCachingProvider(cpn);

        URI cachingConfigUri = getCachingConfigUri(config);
        CacheManager cacheManager = cachingProvider.getCacheManager(cachingConfigUri, getClass().getClassLoader());
        MutableConfiguration<K, V> configuration = new MutableConfiguration().setStoreByValue(false);

        Cache<K, V> cache = cacheManager.createCache(finalCacheId, configuration);
        logger.info("Cache is ready for {}", cacheId);

        return cache;
    }

    private String getCachingProviderName(Configuration config) {
        String cachingProviderName = JkeelCacheConst.CACHING_PROVIDER_NAME;
        if (config != null) {
            String cpn = config.get(JkeelCacheConst.CACHING_PROVIDER_NAME_KEY);
            if (cpn != null) {
                cachingProviderName = cpn;
            }
        }
        logger.info("Cache Provider : {}", cachingProviderName);
        return cachingProviderName;
    }

    private URI getCachingConfigUri(Configuration config) {
        String cachingConfigUri = JkeelCacheConst.CACHING_CONFIG_FILE_NAME;
        try {
            if (config != null) {
                String ccu = config.get(JkeelCacheConst.CACHING_CONFIG_FILE_NAME_KEY);
                if (ccu != null) {
                    cachingConfigUri = ccu;
                }
            }
            logger.info("Cache Config : {}", cachingConfigUri);
            return getClass().getResource(cachingConfigUri).toURI();
        } catch (Exception e) {
            throw new RuntimeException("Caching Config is not found", e);
        }
    }
}
