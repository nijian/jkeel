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
import java.util.Properties;

/**
 * Serration Config
 *
 * @author nj
 * @since 0.0.1
 */
public class SerrationConfig implements AlgorithmConfig {

    private static Logger logger = LoggerFactory.getLogger(SerrationConfig.class);

    private Cache<String, Closure> cache;
    private String cid;
    private Properties env;
    private boolean init = false;

    @Override
    public void init(String cid, String configUri, Properties env) {
        try {
            if (!init) {
                this.env = env;

                String cachingProviderName = "org.ehcache.jsr107.EhcacheCachingProvider";
                CachingProvider cachingProvider = Caching.getCachingProvider(cachingProviderName);
                logger.info("Cache Provider:{}", cachingProviderName);
                CacheManager cacheManager = cachingProvider.getCacheManager(
                        getClass().getResource("/serration-ehcache.xml").toURI(),
                        getClass().getClassLoader());
                MutableConfiguration<String, Closure> configuration = new MutableConfiguration<String, Closure>().setStoreByValue(false);
                cache = cacheManager.createCache(cid, configuration);
                logger.info("Cache is ready for {}", cid);

                Class configClz = Class.forName("com.github.nijian.jkeel.algorithms.serration.CalcConfig");
                Object configInstance = configClz.getDeclaredConstructor(Cache.class).newInstance(cache);

                CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
                compilerConfiguration.setSourceEncoding("UTF-8");
                compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8);
                GroovyClassLoader loader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), compilerConfiguration, false);
                String content = IOGroovyMethods.getText(getClass().getResourceAsStream(configUri));
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

    public void setCache(Cache<String, Closure> cache) {
        this.cache = cache;
    }

    @Override
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public Properties getEnv() {
        return env;
    }

    public void setEnv(Properties env) {
        this.env = env;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
}
