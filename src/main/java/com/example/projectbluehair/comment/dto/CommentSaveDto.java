package com.example.projectbluehair.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveDto {
    private String content;
    private Long parentCommentId;

    @Builder
    public CommentSaveDto(String content, Long parentCommentId) {
        this.content = content;
        this.parentCommentId = parentCommentId;
    }
}
