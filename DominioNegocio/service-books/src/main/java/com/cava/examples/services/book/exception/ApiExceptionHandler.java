package com.cava.examples.services.book.exception;

import com.cava.examples.services.book.modelError.Error;
import com.cava.examples.services.book.modelError.ModelError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ModelError> HandleException(Exception e){
        ModelError model = new ModelError(Error.ERROR_HANDLER.getCode(), Error.ERROR_HANDLER.getMessage(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(model);
    }
}
