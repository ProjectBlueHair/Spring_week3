package com.example.projectbluehair.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCustomException extends RuntimeException {
    private final MemberErrorCode errorCode;
}
