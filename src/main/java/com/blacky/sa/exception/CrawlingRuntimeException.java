package com.blacky.sa.exception;

public class CrawlingRuntimeException extends RuntimeException {

    public CrawlingRuntimeException() {
        super();
    }

    public CrawlingRuntimeException(String msg) {
        super(msg);
    }

    public CrawlingRuntimeException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
