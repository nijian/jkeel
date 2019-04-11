package com.github.nijian.jkeel.commons;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ObjectHolder provides a list of object which has time consuming construction.
 *
 * @author nj
 * @since 0.0.2
 */
public final class ObjectHolder {

    /**
     * Jackson ObjectMapper
     */
    public final static ObjectMapper objectMapper = new ObjectMapper();

}
