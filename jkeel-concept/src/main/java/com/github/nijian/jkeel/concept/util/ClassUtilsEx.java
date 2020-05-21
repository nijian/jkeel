package com.github.nijian.jkeel.concept.util;

import org.apache.commons.lang3.ClassUtils;

public class ClassUtilsEx {

    public static boolean isAssignable(Object object, final String toClassName) throws ClassNotFoundException {
        Class<?> toClass = ClassUtils.getClass(toClassName);
        if (ClassUtils.isAssignable(object.getClass(), toClass)) {
            return true;
        }
        return false;
    }
}
