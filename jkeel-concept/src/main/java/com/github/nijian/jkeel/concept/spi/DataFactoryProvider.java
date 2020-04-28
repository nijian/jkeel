package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.DataAccessor;

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

    public DataAccessor getData(String name) {

        DataAccessor dataAccessor = null;
        Iterator<DataFactory> factories = loader.iterator();
        while (dataAccessor == null && factories.hasNext()) {
            DataFactory factory = factories.next();
            dataAccessor = factory.getData(name);
        }

        if(dataAccessor ==null){
            throw new RuntimeException("Dao Not Found");
        }
        return dataAccessor;
    }
}
