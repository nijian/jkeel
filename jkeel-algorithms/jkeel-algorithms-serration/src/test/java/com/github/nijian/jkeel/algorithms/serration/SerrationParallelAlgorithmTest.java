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
    private String illustrationCalcTemplateKey1;

    @Before
    public void setUp() throws Exception {
        CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
        cacheManager = cachingProvider.getCacheManager(
                getClass().getResource("/serration-ehcache.xml").toURI(),
                getClass().getClassLoader());

        illustrationCalcTemplateKey1 = "[tenantCode:Baoviet_VN, productCode:BV-NCUVL01, productVersion:v1, illustrationCode:MAIN, illustrationVersion:v1]";

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void perform() throws Exception {

        LayoutTemplate layoutTemplate1 = objectMapper.readValue(getClass().getResourceAsStream("/a.json"), LayoutTemplate.class);

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

        for (int i = 0; i < 20; i++) {
            algorithm.perform(null, varMap, ac);
        }

        algorithm.perform(null, varMap, ac);

        assertEquals(1, 1);
    }
}