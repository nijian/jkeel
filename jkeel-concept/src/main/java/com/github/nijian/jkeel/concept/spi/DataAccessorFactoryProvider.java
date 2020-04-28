package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.DataAccessor;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DataAccessorFactoryProvider {

    private static DataAccessorFactoryProvider provider;

    private ServiceLoader<DataAccessorFactory> loader;

    private DataAccessorFactoryProvider() {
        loader = ServiceLoader.load(DataAccessorFactory.class);
    }

    public static DataAccessorFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (DataAccessorFactoryProvider.class) {
            if (provider == null) {
                provider = new DataAccessorFactoryProvider();
            }
            return provider;
        }
    }

    public DataAccessor getData(String name) {

        DataAccessor dataAccessor = null;
        Iterator<DataAccessorFactory> factories = loader.iterator();
        while (dataAccessor == null && factories.hasNext()) {
            DataAccessorFactory factory = factories.next();
            dataAccessor = factory.getDataAccessor(name);
        }

        if(dataAccessor ==null){
            throw new RuntimeException("Dao Not Found");
        }
        return dataAccessor;
    }
}
