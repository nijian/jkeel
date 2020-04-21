package com.github.nijian.jkeel.concept.spi;

import com.github.nijian.jkeel.concept.Action;

public interface ActionFactory {

    Action getAction(String name);

}
