package com.github.nijian.jkeel.concept;

import com.github.nijian.jkeel.concept.config.ServicesConfig;

import javax.xml.bind.JAXBContext;
import java.io.StringReader;

public abstract class Config {

    public final static String SERVICES = "services";

    private ServicesConfig servicesConfig;

    public abstract String get(String term);

    public ServicesConfig getServicesConfig() {

        if (servicesConfig == null) {
            String servicesConfigString = get(Config.SERVICES);
            StringReader servicesConfigReader = new StringReader(servicesConfigString);
            try {
                JAXBContext context = JAXBContext.newInstance(ServicesConfig.class);
                servicesConfig = (ServicesConfig) context.createUnmarshaller()
                        .unmarshal(servicesConfigReader);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return servicesConfig;
    }

}
