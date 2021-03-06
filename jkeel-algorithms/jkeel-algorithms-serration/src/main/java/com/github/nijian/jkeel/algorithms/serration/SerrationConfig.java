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
     * negative to zero or not
     */
    private boolean negToZero;

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
    public void init(final String cid, final URI configUri, final Class<?> delegateConfigClz, final Properties env) {
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

                String calcConfigClzName = env == null ? null : env.getProperty(Const.CALC_CONFIG_CLASS_NAME_KEY);
                if (calcConfigClzName == null) {
                    calcConfigClzName = Const.CALC_CONFIG_CLASS_NAME;
                }
                Class<?> calcConfigClz = Class.forName(calcConfigClzName);
                logger.info("Calc config class name:{}", calcConfigClz);
                AbstractCalcConfig configInstance;
                try {
                    configInstance = (AbstractCalcConfig) calcConfigClz.getDeclaredConstructor(Cache.class).newInstance(cache);
                    this.delegate = configInstance;
                } catch (Exception e) {
                    logger.error("Calc config class type should be CalcConfig", e);
                    throw new RuntimeException("Calc config class type should be CalcConfig", e);
                }

                CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
                compilerConfiguration.setSourceEncoding("UTF-8");
                compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8);
                GroovyClassLoader loader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), compilerConfiguration, false);

                InputStream config = configUri.toURL().openStream();
                String content = IOGroovyMethods.getText(config);
                Class<?> clazz = loader.parseClass(content);
                Binding binding = new Binding();
                binding.setVariable(calcConfigClz.getSimpleName(), configInstance);
                InvokerHelper.createScript(clazz, binding).run();

                //associated properties
                this.negToZero = configInstance.isNegToZero();

                init = true;
                logger.info("SerrationConfig is initialized for {}", cid);
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
    public boolean isNegToZero() {
        return negToZero;
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
