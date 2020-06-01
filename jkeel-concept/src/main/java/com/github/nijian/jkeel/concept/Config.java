package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.BehaviorsConfig;
import com.github.nijian.jkeel.concept.config.RootConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public abstract class Config {

    private final static String SERVICES = "root";

    private BehaviorsConfig behaviorsConfig;

    public abstract String get(String term);

    BehaviorsConfig getBehaviorsConfig() {

        if (behaviorsConfig == null) {
            String servicesConfigString = get(Config.SERVICES);
            StringReader servicesConfigReader = new StringReader(servicesConfigString);
            try {
                JAXBContext context = JAXBContext.newInstance(RootConfig.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                RootConfig rootConfig = (RootConfig) unmarshaller.unmarshal(servicesConfigReader);
                behaviorsConfig = rootConfig.getBehaviorsConfig();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return behaviorsConfig;
    }

}
