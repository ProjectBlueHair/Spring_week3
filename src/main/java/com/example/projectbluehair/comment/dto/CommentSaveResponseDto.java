package com.example.projectbluehair.comment.dto;

import com.example.projectbluehair.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentSaveResponseDto {
    private String content;
    private LocalDateTime createdAt;
    private long memberId;

    public CommentSaveResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.memberId = comment.getMember().getId();
    }
}
