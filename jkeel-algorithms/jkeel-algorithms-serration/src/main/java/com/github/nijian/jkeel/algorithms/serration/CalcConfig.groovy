package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.AlgorithmConfig
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.runtime.IOGroovyMethods
import org.codehaus.groovy.runtime.InvokerHelper

import javax.cache.Cache
import javax.cache.CacheManager
import javax.cache.Caching
import javax.cache.configuration.MutableConfiguration
import javax.cache.spi.CachingProvider

class CalcConfig implements AlgorithmConfig, MixinFuncs {

    Cache<String, Closure> cache
    String cid
    boolean inited = false

    @Override
    void init(String cid, String configUri, Properties env) {
        if (!inited) {
            CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider")
            CacheManager cacheManager = cachingProvider.getCacheManager(
                    getClass().getResource("/serration-ehcache.xml").toURI(),
                    getClass().getClassLoader())
            MutableConfiguration<String, Closure> configuration = new MutableConfiguration<String, Closure>().setStoreByValue(false)
            cache = cacheManager.createCache(cid, configuration)

            CompilerConfiguration compilerConfiguration = new CompilerConfiguration()
            compilerConfiguration.setSourceEncoding("UTF-8")
            compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8)
            GroovyClassLoader loader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), compilerConfiguration, false)
            String content = IOGroovyMethods.getText(getClass().getResourceAsStream(configUri))
            Class<?> clazz = loader.parseClass(content)
            Binding binding = new Binding()
            binding.setVariable("CalcConfig", this)
            InvokerHelper.createScript(clazz, binding).run()
            inited = true
        }
    }

    Cache<String, Closure> getCache() {
        return cache
    }

    def cid(String cid) {
        this.cid = cid
    }

    def call(Closure closure) {
        closure.setDelegate(this)
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.call()
    }

    def Convert(Closure closure) {
        cache.put(Const.CONVERT, closure)
    }

    def Output(Closure closure) {
        cache.put(Const.OUTPUT, closure)
    }

    def itemGroupCount(String name, Closure<Integer> closure) {
        cache.put(name, closure)
    }

    def layoutCount(String name, Closure<Integer> closure) {
        cache.put(name, closure)
    }

    def formula(String name, Closure<BigDecimal> closure) {
        cache.put(name, closure)
    }
}
