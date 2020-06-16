package com.github.nijian.jkeel.converter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class Json2XmlTest {

    @Test
    public void convert() {
        try {

            String json = IOUtils.toString(this.getClass().getResourceAsStream("/Source.json"), StandardCharsets.UTF_8);
            String xsd = IOUtils.toString(this.getClass().getResourceAsStream("/A.xsd"), StandardCharsets.UTF_8);

            Json2Xml j = new Json2Xml();

            System.out.println(j.convert(json, xsd));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}