package com.cava.examples.services.book.modelError;

public enum Error {

    ERROR_HANDLER(300,"error general");
    private final int code;
    private final String message;

    Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
