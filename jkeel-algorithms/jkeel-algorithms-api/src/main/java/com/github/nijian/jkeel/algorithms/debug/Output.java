package com.github.nijian.jkeel.algorithms.debug;

/**
 * Output is for debugging.
 *
 * @param <R> calculation result type
 * @param <S> output specification type
 * @author nj
 * @since 0.0.1
 */
public interface Output<R, S> {

    /**
     * Write calculation result to output with specification
     *
     * @param result calculation result
     * @param spec   output specification
     */
    void write(R result, S spec);
}
