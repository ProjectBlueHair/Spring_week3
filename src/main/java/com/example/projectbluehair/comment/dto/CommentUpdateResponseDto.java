package com.example.projectbluehair.comment.dto;

import com.example.projectbluehair.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponseDto {
    private String content;
    private LocalDateTime createdAt;
    private long memberId;

    public CommentUpdateResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.memberId = comment.getMember().getId();
    }
}
