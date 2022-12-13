package com.example.projectbluehair.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {
    private String content;

    @Builder
    public CommentDto(String content) {
        this.content = content;
    }
}
