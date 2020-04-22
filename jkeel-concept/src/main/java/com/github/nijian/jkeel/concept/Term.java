package com.github.nijian.jkeel.concept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.TYPE_USE, ElementType.LOCAL_VARIABLE})
public @interface Term {

    String value();
}
