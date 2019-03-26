package com.github.nijian.jkeel.algorithms.serration

import com.github.nijian.jkeel.algorithms.Config
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.runtime.IOGroovyMethods
import org.codehaus.groovy.runtime.InvokerHelper

import javax.cache.Cache

class CalcConfig implements Config<Closure>, MixinFuncs {

    Cache<String, Closure<?>> closureCache
    String cid

    CalcConfig() {

    }

    @Override
    void init(Cache cache) {

        if (closureCache == null) {
            closureCache = cache

            CompilerConfiguration compilerConfiguration = new CompilerConfiguration()
            compilerConfiguration.setSourceEncoding("UTF-8")
            compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8)
            GroovyClassLoader loader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), compilerConfiguration, false)
            String content = IOGroovyMethods.getText(getClass().getResourceAsStream("/config.illus"))
            Class<?> clazz = loader.parseClass(content)
            Binding binding = new Binding()
            binding.setVariable("CalcConfig", this)
            InvokerHelper.createScript(clazz, binding).run()
        }
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
        closureCache.put(Const.CONVERT, closure)
    }

    def Output(Closure closure) {
        closureCache.put(Const.OUTPUT, closure)
    }

    def itemGroupCount(String name, Closure<Integer> closure) {
        closureCache.put(name, closure)
    }

    def layoutCount(String name, Closure<Integer> closure) {
        closureCache.put(name, closure)
    }

    def formula(String name, Closure<BigDecimal> closure) {
        closureCache.put(name, closure)
    }
}
