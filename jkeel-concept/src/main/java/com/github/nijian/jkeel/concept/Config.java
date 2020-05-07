package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.BehaviorsConfig;
import com.github.nijian.jkeel.concept.config.RootConfig;

import javax.xml.bind.JAXBContext;
import java.io.StringReader;

public abstract class Config {

    public final static String SERVICES = "root";

    private BehaviorsConfig behaviorsConfig;

    public abstract String get(String term);

    public BehaviorsConfig getBehaviorsConfig() {

        if (behaviorsConfig == null) {
            String servicesConfigString = get(Config.SERVICES);
            StringReader servicesConfigReader = new StringReader(servicesConfigString);
            try {
                JAXBContext context = JAXBContext.newInstance(RootConfig.class);
                RootConfig rootConfig = (RootConfig) context.createUnmarshaller()
                        .unmarshal(servicesConfigReader);
                behaviorsConfig = rootConfig.getBehaviorsConfig();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return behaviorsConfig;
    }

}
