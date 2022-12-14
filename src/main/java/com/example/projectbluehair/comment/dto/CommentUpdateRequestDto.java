package com.example.projectbluehair.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String content;

    public CommentUpdateDto tocommentDto(){
        return CommentUpdateDto.builder()
                .content(content)
                .build();
    }
}
