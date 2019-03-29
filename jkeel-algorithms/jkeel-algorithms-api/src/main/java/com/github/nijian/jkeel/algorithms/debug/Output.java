package com.github.nijian.jkeel.algorithms.debug;

/**
 * Output is for debugging.
 *
 * @param <R> calculation result
 * @param <S> output specification
 * @author nj
 * @since 0.0.1
 */
public interface Output<R, S> {

    void write(R result, S spec);
}
