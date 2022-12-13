package com.example.projectbluehair.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {
    private String content;
    private Long parentCommentId;

    @Builder
    public CommentDto(String content, Long parentCommentId) {
        this.content = content;
        this.parentCommentId = parentCommentId;
    }
}
