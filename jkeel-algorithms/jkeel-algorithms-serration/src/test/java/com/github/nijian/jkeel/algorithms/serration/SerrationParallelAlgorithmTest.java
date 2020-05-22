package com.github.nijian.jkeel.algorithms.serration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nijian.jkeel.algorithms.*;


import com.github.nijian.jkeel.algorithms.serration.entity.LayoutTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class SerrationParallelAlgorithmTest {

    Logger logger = LoggerFactory.getLogger(SerrationParallelAlgorithmTest.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private static String cid;
    private static LayoutTemplate layoutTemplate1;
    private static Map<String, Object> varMap = new HashMap<>();
    private static Algorithm algorithm;
    private static AlgorithmContext ac;
    private static Properties env = new Properties();


    @BeforeClass
    public static void setUp0() throws Exception {


//        env.setProperty(Const.CACHING_PROVIDER_NAME_KEY, "org.ehcache.jsr107.EhcacheCachingProvider");
        env.setProperty(Const.CACHING_PROVIDER_NAME_KEY, "org.ehcache.jcache.JCacheCachingProvider");
        env.setProperty(Const.CALC_CLASS_NAME_KEY, "com.github.nijian.jkeel.algorithms.serration.MyCalc");
        env.setProperty(Const.CALC_CONFIG_CLASS_NAME_KEY, "com.github.nijian.jkeel.algorithms.serration.MyCalcConfig");

        //prepare variables map
        Map<String, Double> unitPriceMapH = IllustrationHelper.buildUnitPriceRateTable0(new Date(119, 1, 1), new Date(118, 0, 1), 10000d, 'H');
        Map<String, Double> unitPriceMapL = IllustrationHelper.buildUnitPriceRateTable0(new Date(119, 1, 1), new Date(118, 0, 1), 10000d, 'L');
        Map<String, Double> unitPriceMapG = IllustrationHelper.buildUnitPriceRateTable0(new Date(119, 1, 1), new Date(118, 0, 1), 10000d, 'G');
        varMap.put("unitPriceMapH", unitPriceMapH);
        varMap.put("unitPriceMapL", unitPriceMapL);
        varMap.put("unitPriceMapG", unitPriceMapG);

        cid = "[Test1]";
        layoutTemplate1 = objectMapper.readValue(cid.getClass().getResourceAsStream("/a.json"), LayoutTemplate.class);
        algorithm = AlgorithmFactoryProvider.getInstance().getAlgorithm(Serration.class.getName());
        URL url = cid.getClass().getResource("/config.illus");
        ac = AlgorithmContextManager.getInstance().createTemplateContext(cid, layoutTemplate1, url.toURI(), SerrationConfig.class, env);
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void perform0() throws Exception {
        algorithm.perform(null, varMap, ac, true);
        Thread.sleep(1000 * 5);
        assertEquals(1, 1);
    }

    @Test
    public void perform1() throws Exception {
//        algorithm.perform(null, varMap, ac);
//        Thread.sleep(1000 * 5);
//        assertEquals(1, 1);

    }

}