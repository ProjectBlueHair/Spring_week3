package com.example.projectbluehair.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateDto {
    private String content;

    @Builder
    public CommentUpdateDto(String content) {
        this.content = content;
    }
}
