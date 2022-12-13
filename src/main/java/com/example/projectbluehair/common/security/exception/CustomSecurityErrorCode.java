package com.example.projectbluehair.common.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum CustomSecurityErrorCode {
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    JWT_NOT_FOUND(UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    INVALID_JWT(UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "작성자만 삭제/수정할 수 있습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다");

    private final HttpStatus httpStatus;
    private final String detail;
}
