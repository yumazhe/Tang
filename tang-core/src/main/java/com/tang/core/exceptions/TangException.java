package com.tang.core.exceptions;

/**
 * Created by yuma on 2019/12/9.
 */
public class TangException extends RuntimeException {

    public TangException() {
        super();
    }

    public TangException(String message) {
        super(message);
    }

    public TangException(String message, Throwable cause) {
        super(message, cause);
    }

    public TangException(Throwable cause) {
        super(cause);
    }

    protected TangException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
