package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.BehaviorsConfig;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

public class ConfigTest {

    @Test
    public void getServicesConfig() {

        Config config = new Config() {
            @Override
            public String get(String term) {
                try {
                    File file = new File(getClass().getClassLoader().getResource("services.xml").getFile());
                    return FileUtils.readFileToString(file, "UTF-8");
                } catch (Exception e) {
                    throw new RuntimeException("xxxxxxx");
                }
            }
        };

        BehaviorsConfig behaviorsConfig = config.getBehaviorsConfig();
        System.out.println(behaviorsConfig);

    }
}