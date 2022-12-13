package com.example.projectbluehair.forum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomForumException extends RuntimeException {
    private final CustomForumErrorCode errorCode;
}
