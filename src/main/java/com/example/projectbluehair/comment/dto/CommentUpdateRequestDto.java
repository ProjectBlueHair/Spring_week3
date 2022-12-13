package com.example.projectbluehair.comment.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
    private String content;

    public CommentDto tocommentDto(){
        return CommentDto.builder()
                .content(content)
                .build();
    }
}
