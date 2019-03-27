package com.github.nijian.jkeel.algorithms.serration.debug;

import java.util.function.BiConsumer;

public class Iterables {

    public static <E> void forEach(
            Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }
}
