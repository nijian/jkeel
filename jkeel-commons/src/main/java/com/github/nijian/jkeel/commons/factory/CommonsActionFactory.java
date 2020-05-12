package com.github.nijian.jkeel.commons.factory;

import com.github.nijian.jkeel.commons.action.PopulateAction;
import com.github.nijian.jkeel.commons.service.JsonService;
import com.github.nijian.jkeel.concept.Action;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.concept.spi.ActionFactory;
import com.github.nijian.jkeel.concept.spi.ServiceFactory;

public final class CommonsActionFactory implements ActionFactory {

    @Override
    public Action getAction(String actionName) {
        if (actionName.equals("POPULATE")) {
            return new PopulateAction();
        }

        return null;
    }
}
