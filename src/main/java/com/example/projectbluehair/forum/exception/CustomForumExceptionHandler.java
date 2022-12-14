package com.example.projectbluehair.forum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomForumExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {CustomForumException.class})
    protected ResponseEntity<CustomForumErrorResponse> handleCustomException(CustomForumException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return CustomForumErrorResponse.toResponseEntity(e.getErrorCode());
    }
}