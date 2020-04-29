package com.github.nijian.jkeel.commons.query.service;

import com.github.nijian.jkeel.concept.ConceptInput;
import com.github.nijian.jkeel.concept.Manager;
import com.github.nijian.jkeel.concept.Service;
import com.github.nijian.jkeel.commons.query.entity.Query;

public abstract class QueryService<R> extends Service<Query, R> {

    protected void checkInput(ConceptInput<?, Query> serviceInput) {

    }

}
