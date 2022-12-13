package com.example.projectbluehair.comment.dto;

import lombok.Getter;

@Getter
public class CommentSaveRequestDto {

    private String content;
    private Long parentCommentId;

    public CommentDto tocommentDto(){
        return CommentDto.builder()
                .content(content)
                .parentCommentId(parentCommentId)
                .build();
    }
}
