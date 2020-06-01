package com.github.nijian.jkeel.concept;

public class BehaviorException extends RuntimeException {

    public BehaviorException(String message) {
        super(message);
    }

    public BehaviorException(String message, Throwable cause) {
        super(message, cause);
    }
}
