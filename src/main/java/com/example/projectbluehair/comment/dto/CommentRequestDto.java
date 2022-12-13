package com.example.projectbluehair.comment.dto;

import com.example.projectbluehair.member.dto.SignUpDto;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private String content;

    public CommentDto tocommentDto(){
        return CommentDto.builder()
                .content(content)
                .build();
    }
}
