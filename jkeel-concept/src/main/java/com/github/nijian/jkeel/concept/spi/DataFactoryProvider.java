package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Data;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DataFactoryProvider {

    private static DataFactoryProvider provider;

    private ServiceLoader<DataFactory> loader;

    private DataFactoryProvider() {
        loader = ServiceLoader.load(DataFactory.class);
    }

    public static DataFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (DataFactoryProvider.class) {
            if (provider == null) {
                provider = new DataFactoryProvider();
            }
            return provider;
        }
    }

    public Data getData(String name) {

        Data data = null;
        Iterator<DataFactory> factories = loader.iterator();
        while (data == null && factories.hasNext()) {
            DataFactory factory = factories.next();
            data = factory.getData(name);
        }

        if(data==null){
            throw new RuntimeException("Dao Not Found");
        }
        return data;
    }
}
