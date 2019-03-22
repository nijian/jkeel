package com.github.nijian.jkeel.algorithms.serration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nijian.jkeel.algorithms.Serration;


import com.github.nijian.jkeel.algorithms.serration.entity.LayoutTemplate;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.IOGroovyMethods;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SerrationParallelAlgorithmTest {

    Logger logger = LoggerFactory.getLogger(SerrationParallelAlgorithmTest.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private String illustrationCalcTemplateKey1;

//    private String illustrationCalcTemplateKey2;

    @Before
    public void setUp() throws Exception {
        //compile DSL and cache closure
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("UTF-8");
        compilerConfiguration.setTargetBytecode(CompilerConfiguration.JDK8);
        GroovyClassLoader loader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), compilerConfiguration, false);
        String content = IOGroovyMethods.getText(getClass().getResourceAsStream("/config.dsl"));
        Class<?> clazz = loader.parseClass(content);
        Binding binding = new Binding();
        binding.setVariable("CalcConfig", new CalcConfig());
        InvokerHelper.createScript(clazz, binding).run();

//        content = IOGroovyMethods.getText(getClass().getResourceAsStream("/longevity.dsl"));
//        clazz = loader.parseClass(content);
//        binding = new Binding();
//        binding.setVariable("CalcConfig", new CalcConfig());
//        InvokerHelper.createScript(clazz, binding).run();

        LayoutTemplate layoutTemplate1 = objectMapper.readValue(getClass().getResourceAsStream("/a.json"), LayoutTemplate.class);
//        Template layoutTemplate2 = objectMapper.readValue(getClass().getResourceAsStream("/b.json"), Template.class);

        illustrationCalcTemplateKey1 = "[tenantCode:Baoviet_VN, productCode:BV-NCUVL01, productVersion:v1, illustrationCode:MAIN, illustrationVersion:v1]";
        CalcCache.put(illustrationCalcTemplateKey1, layoutTemplate1);
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
//        algorithm.perform(null, varMap, CalcCache.get(illustrationCalcTemplateKey1));

        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
//            algorithm.perform(null, varMap, CalcCache.get(illustrationCalcTemplateKey1));
            logger.info("execution time : {}ms", (System.currentTimeMillis() - start));
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