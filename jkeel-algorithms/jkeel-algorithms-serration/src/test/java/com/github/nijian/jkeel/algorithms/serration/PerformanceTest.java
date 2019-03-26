package com.github.nijian.jkeel.algorithms.serration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nijian.jkeel.algorithms.AlgorithmContext;
import com.github.nijian.jkeel.algorithms.Config;
import com.github.nijian.jkeel.algorithms.Serration;


import com.github.nijian.jkeel.algorithms.Template;
import com.github.nijian.jkeel.algorithms.serration.entity.LayoutTemplate;
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
import javax.cache.spi.CachingProvider;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * For performance tuning
 */
public class PerformanceTest {

    Logger logger = LoggerFactory.getLogger(PerformanceTest.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private String illustrationCalcTemplateKey1;

    private String illustrationCalcTemplateKey2;

    public final static void main(String[] args) {

        try {
            new PerformanceTest().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void perform() throws Exception {
        CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
        CacheManager cacheManager = cachingProvider.getCacheManager(
                getClass().getResource("/serration-ehcache.xml").toURI(),
                getClass().getClassLoader());
        Cache<String, LayoutTemplate> templateCache = cacheManager.getCache("template-cache", String.class, LayoutTemplate.class);
        Cache<String, Closure> closureCache = cacheManager.getCache("closure-cache", String.class, Closure.class);

        //compile DSL and cache closure
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("UTF-8");
        compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8);
        GroovyClassLoader loader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), compilerConfiguration, false);
        String content = IOGroovyMethods.getText(getClass().getResourceAsStream("/config.illus"));
        Class<?> clazz = loader.parseClass(content);
        Binding binding = new Binding();
        Config config = new CalcConfig();
        config.init(closureCache);
        binding.setVariable("CalcConfig", config);
        InvokerHelper.createScript(clazz, binding).run();

        logger.info("xxxxxxxxxxxxxxxxxx");

//        content = IOGroovyMethods.getText(getClass().getResourceAsStream("/longevity.dsl"));
//        clazz = loader.parseClass(content);
//        binding = new Binding();
//        binding.setVariable("CalcConfig", new CalcConfig());
//        InvokerHelper.createScript(clazz, binding).run();

        LayoutTemplate layoutTemplate1 = objectMapper.readValue(getClass().getResourceAsStream("/a.json"), LayoutTemplate.class);
//        Template layoutTemplate2 = objectMapper.readValue(getClass().getResourceAsStream("/b.json"), Template.class);

        illustrationCalcTemplateKey1 = "[tenantCode:Baoviet_VN, productCode:BV-NCUVL01, productVersion:v1, illustrationCode:MAIN, illustrationVersion:v1]";
        templateCache.put(illustrationCalcTemplateKey1, layoutTemplate1);
//        illustrationCalcTemplateKey2 = "[tenantCode:Baoviet_VN, productCode:BV-NCUVL01, productVersion:v1, illustrationCode:MAIN_LB, illustrationVersion:v1]";
//        CalcCache.put(illustrationCalcTemplateKey2, layoutTemplate2);

        //prepare variables map
        Map<String, Object> varMap = new HashMap<>();
        Map<String, Double> unitPriceMapH = IllustrationHelper.buildUnitPriceRateTable0(new Date(119, 1, 1), new Date(118, 0, 1), 10000d, 'H');
        Map<String, Double> unitPriceMapL = IllustrationHelper.buildUnitPriceRateTable0(new Date(119, 1, 1), new Date(118, 0, 1), 10000d, 'L');
        Map<String, Double> unitPriceMapG = IllustrationHelper.buildUnitPriceRateTable0(new Date(119, 1, 1), new Date(118, 0, 1), 10000d, 'G');
        varMap.put("unitPriceMapH", unitPriceMapH);
        varMap.put("unitPriceMapL", unitPriceMapL);
        varMap.put("unitPriceMapG", unitPriceMapG);

        Serration<Date> algorithm = new Serration<>();
        AlgorithmContext ac = new AlgorithmContext(illustrationCalcTemplateKey1, layoutTemplate1, new CalcConfig(), cacheManager);
        algorithm.perform(null, varMap, ac);

        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            algorithm.perform(null, varMap, ac);
            logger.info("execution time : {}ms", (System.currentTimeMillis() - start));
        }

//        varMap.put("lbTermM", 60);
//        varMap.put("lbStartIndex", 10);
//        varMap.put("PAV_AB_end_H", 100000000);
//        varMap.put("PAV_AB_end_L", 100000000);
//        varMap.put("PAV_AB_end_G", new Integer(100000000));
//        algorithm.perform(null, varMap, CalcCache.get(illustrationCalcTemplateKey2));

    }
}
