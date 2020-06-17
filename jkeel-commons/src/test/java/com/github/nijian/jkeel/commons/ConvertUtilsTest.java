package com.github.nijian.jkeel.commons;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class ConvertUtilsTest {

    @Test
    public void jsonToXml() {

        try {

            String json = IOUtils.toString(this.getClass().getResourceAsStream("/Source.json"), StandardCharsets.UTF_8);
            String xsd = IOUtils.toString(this.getClass().getResourceAsStream("/ConvertUtilsTest.xsd"), StandardCharsets.UTF_8);

            System.out.println(ConvertUtils.jsonToXml(json, xsd));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}