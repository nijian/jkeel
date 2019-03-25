package com.github.nijian.jkeel.algorithms.serration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nijian.jkeel.algorithms.AlgorithmContext;
import com.github.nijian.jkeel.algorithms.Config;
import com.github.nijian.jkeel.algorithms.Serration;


import com.github.nijian.jkeel.algorithms.serration.entity.LayoutTemplate;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.IOGroovyMethods;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SerrationParallelAlgorithmTest {

    Logger logger = LoggerFactory.getLogger(SerrationParallelAlgorithmTest.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private CacheManager cacheManager;
    private Cache<String, LayoutTemplate> templateCache;
    private Cache<String, Closure> closureCache;
    private String illustrationCalcTemplateKey1;

//    private String illustrationCalcTemplateKey2;

    @Before
    public void setUp() throws Exception {
        CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
        cacheManager = cachingProvider.getCacheManager(
                getClass().getResource("/serration-ehcache.xml").toURI(),
                getClass().getClassLoader());
        templateCache = cacheManager.getCache("template-cache", String.class, LayoutTemplate.class);
        closureCache = cacheManager.getCache("closure-cache", String.class, Closure.class);

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

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void perform() {

        //prepare variables map
        Map<String, Object> varMap = new HashMap<>();
        Map<String, Double> unitPriceMapH = IllustrationHelper.buildUnitPriceRateTable0(new Date(119, 1, 1), new Date(118, 0, 1), 10000d, 'H');
        Map<String, Double> unitPriceMapL = IllustrationHelper.buildUnitPriceRateTable0(new Date(119, 1, 1), new Date(118, 0, 1), 10000d, 'L');
        Map<String, Double> unitPriceMapG = IllustrationHelper.buildUnitPriceRateTable0(new Date(119, 1, 1), new Date(118, 0, 1), 10000d, 'G');
        varMap.put("unitPriceMapH", unitPriceMapH);
        varMap.put("unitPriceMapL", unitPriceMapL);
        varMap.put("unitPriceMapG", unitPriceMapG);

        Serration<Date> algorithm = new Serration<>();
        AlgorithmContext ac = new AlgorithmContext(templateCache.get(illustrationCalcTemplateKey1), closureCache);
        algorithm.perform(null, varMap, ac);

        for (int i = 0; i < 100; i++) {
            algorithm.perform(null, varMap, ac);
        }
//        varMap.put("lbTermM", 60);
//        varMap.put("lbStartIndex", 10);
//        varMap.put("PAV_AB_end_H", 100000000);
//        varMap.put("PAV_AB_end_L", 100000000);
//        varMap.put("PAV_AB_end_G", new Integer(100000000));
//        algorithm.perform(null, varMap, CalcCache.get(illustrationCalcTemplateKey2));

        assertEquals(1, 1);
    }
}