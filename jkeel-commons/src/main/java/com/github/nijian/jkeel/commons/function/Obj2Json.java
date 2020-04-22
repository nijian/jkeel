package com.github.nijian.jkeel.commons.function;

import com.github.nijian.jkeel.commons.ObjectHolder;

import java.util.function.Function;

public class Obj2Json implements Function<Object, String> {

    public Obj2Json(Object obj){

    }
    @Override
    public String apply(Object o) {
        try {
            return ObjectHolder.objectMapper.writeValueAsString(o);
        }catch(Exception e){
            throw new RuntimeException("xx", e);
        }
    }
}
