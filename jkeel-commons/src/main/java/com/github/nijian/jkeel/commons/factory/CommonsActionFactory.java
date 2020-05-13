package com.github.nijian.jkeel.commons.factory;

import com.github.nijian.jkeel.commons.action.PopulateAction;
import com.github.nijian.jkeel.concept.Action;
import com.github.nijian.jkeel.concept.spi.ActionFactory;

public final class CommonsActionFactory implements ActionFactory {

    @Override
    public Action getAction(String actionName) {
        if (actionName.equals("POPULATE")) {
            return new PopulateAction();
        }

        return null;
    }
}
