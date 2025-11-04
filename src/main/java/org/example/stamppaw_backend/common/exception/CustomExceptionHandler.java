package org.example.stamppaw_backend.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(StampPawException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(StampPawException e) {
        return ErrorResponseEntity.toResponseEntity((e.errorCode));
    }
}
