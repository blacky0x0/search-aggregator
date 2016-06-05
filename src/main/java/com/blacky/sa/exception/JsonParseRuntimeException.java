package com.blacky.sa.exception;

public class JsonParseRuntimeException extends RuntimeException {

    public JsonParseRuntimeException() {
        super();
    }

    public JsonParseRuntimeException(String msg) {
        super(msg);
    }

    public JsonParseRuntimeException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
