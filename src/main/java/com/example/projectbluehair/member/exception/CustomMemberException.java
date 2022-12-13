package com.example.projectbluehair.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomMemberException extends RuntimeException {
    private final CustomMemberErrorCode errorCode;
}
