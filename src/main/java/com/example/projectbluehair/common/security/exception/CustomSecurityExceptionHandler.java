package com.example.projectbluehair.common.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomSecurityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomSecurityException.class })
    protected ResponseEntity<CustomSecurityErrorResponse> handleCustomException(CustomSecurityException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return CustomSecurityErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
