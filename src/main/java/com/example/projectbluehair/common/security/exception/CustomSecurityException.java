package com.example.projectbluehair.common.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomSecurityException extends RuntimeException {
    private final CustomSecurityErrorCode errorCode;
}
