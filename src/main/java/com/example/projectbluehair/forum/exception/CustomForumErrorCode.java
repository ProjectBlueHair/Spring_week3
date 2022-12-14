package com.example.projectbluehair.forum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum CustomForumErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    FORUM_NOT_PERMISSION(BAD_REQUEST, "게시글 권한이 유효하지 않습니다."),
    FORUM_LIKE_ALREADY_EXIST(BAD_REQUEST, "이미 게시글 좋아요를 하였습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    FORUM_NOT_FOUND(NOT_FOUND, "해당 게시글 정보를 찾을 수 없습니다"),
    FORUM_LIKE_NOT_FOUND(NOT_FOUND, "해당 게시글의 좋아요가 없습니다.");


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    //DUPLICATE_MEMBERNAME(CONFLICT, "중복된 유저이름입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
