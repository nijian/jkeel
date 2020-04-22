package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Action;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ActionFactoryProvider {

    private static ActionFactoryProvider provider;

    private ServiceLoader<ActionFactory> loader;

    private ActionFactoryProvider() {
        loader = ServiceLoader.load(ActionFactory.class);
    }

    public static ActionFactoryProvider getInstance() {
        if (provider != null) {
            return provider;
        }

        synchronized (ActionFactoryProvider.class) {
            if (provider == null) {
                provider = new ActionFactoryProvider();
            }
            return provider;
        }
    }

    public Action getAction(String name) {

        Action action = null;
        Iterator<ActionFactory> factories = loader.iterator();
        while (action == null && factories.hasNext()) {
            ActionFactory factory = factories.next();
            action = factory.getAction(name);
        }

        if (action == null) {
            throw new RuntimeException("Action Not Found");
        }
        return action;
    }
}
