package com.github.nijian.jkeel.algorithms.debug;

/**
 * Output is for debugging.
 *
 * @param <R> calculation result type
 * @param <S> output specification type
 * @param <O> real output type
 * @author nj
 * @since 0.0.1
 */
public interface Output<R, S, O> {

    /**
     * Write calculation result to output with specification
     *
     * @param result calculation result
     * @param spec   output specification
     * @param out    real output
     */
    void write(R result, S spec, O out);

}
