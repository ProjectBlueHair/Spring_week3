package com.example.projectbluehair.forum.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


@Getter
@Builder
public class CustomForumErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<com.example.projectbluehair.forum.exception.CustomForumErrorResponse> toResponseEntity(CustomForumErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(com.example.projectbluehair.forum.exception.CustomForumErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDetail())
                        .build()
                );
    }
}