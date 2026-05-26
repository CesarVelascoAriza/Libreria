package com.cava.examples.services.book.modelError;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelError {
    private int code;
    private String message;
    private String messageError;

    public ModelError() {
    }

    public ModelError(int code, String message, String messageError) {
        this.code = code;
        this.message = message;
        this.messageError = messageError;
    }
}
