package com.github.nijian.jkeel.algorithms.serration;

import com.github.nijian.jkeel.algorithms.AlgorithmConfig;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.IOGroovyMethods;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

/**
 * Serration Config
 *
 * @author nj
 * @since 0.0.1
 */
public class SerrationConfig implements AlgorithmConfig {

    private static Logger logger = LoggerFactory.getLogger(SerrationConfig.class);

    /**
     * closure cache
     */
    private Cache<String, Closure> cache;

    /**
     * algorithm context global identifier
     */
    private String cid;

    /**
     * environment variables
     */
    private Properties env;

    /**
     * config delegate
     */
    private Object delegate;

    /**
     * initialized or not
     */
    private boolean init = false;

    @Override
    public void init(final String cid, final URI configUri, final Properties env) {
        try {
            if (!init) {
                this.env = env;
                this.cid = cid;

                String cachingProviderName = env == null ? null : env.getProperty(Const.CACHING_PROVIDER_NAME_KEY);
                if (cachingProviderName == null) {
                    cachingProviderName = Const.CACHING_PROVIDER_NAME;
                }
                CachingProvider cachingProvider = Caching.getCachingProvider(cachingProviderName);
                logger.info("Cache Provider:{}", cachingProviderName);

                String cacheConfigFileName = env == null ? null : env.getProperty(Const.CACHING_CONFIG_FILE_NAME_KEY);
                if (cacheConfigFileName == null) {
                    cacheConfigFileName = Const.CACHING_CONFIG_FILE_NAME;
                }
                CacheManager cacheManager = cachingProvider.getCacheManager(
                        getClass().getResource(cacheConfigFileName).toURI(),
                        getClass().getClassLoader());
                MutableConfiguration<String, Closure> configuration = new MutableConfiguration<String, Closure>().setStoreByValue(false);
                cache = cacheManager.createCache(cid, configuration);
                logger.info("Cache is ready for {}", cid);

                Class configClz = Class.forName("com.github.nijian.jkeel.algorithms.serration.CalcConfig");
                Object configInstance = configClz.getDeclaredConstructor(Cache.class).newInstance(cache);
                this.delegate = configInstance;

                CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
                compilerConfiguration.setSourceEncoding("UTF-8");
                compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8);
                GroovyClassLoader loader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), compilerConfiguration, false);

                InputStream config = configUri.toURL().openStream();
                String content = IOGroovyMethods.getText(config);
                Class<?> clazz = loader.parseClass(content);
                Binding binding = new Binding();
                binding.setVariable("CalcConfig", configInstance);
                InvokerHelper.createScript(clazz, binding).run();
                init = true;
            }
        } catch (Exception e) {
            logger.error("Failed to init algorithm config", e);
            throw new RuntimeException("Failed to init algorithm config", e);
        }
    }

    public Cache<String, Closure> getCache() {
        return cache;
    }

    @Override
    public String getCid() {
        return cid;
    }

    @Override
    public Properties getEnv() {
        return env;
    }

    @Override
    public Object getDelegate() {
        return delegate;
    }
}
