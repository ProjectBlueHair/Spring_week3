package com.example.projectbluehair.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private String content;
    private Long parentCommentId;

    public CommentSaveDto tocommentDto(){
        return CommentSaveDto.builder()
                .content(content)
                .parentCommentId(parentCommentId)
                .build();
    }
}
