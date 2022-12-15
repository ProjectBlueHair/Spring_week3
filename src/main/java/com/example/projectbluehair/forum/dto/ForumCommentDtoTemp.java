package com.example.projectbluehair.forum.dto;

import com.example.projectbluehair.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ForumCommentDtoTemp {
    private String content;
    private LocalDateTime createdAt;
    private String memberName;


    public ForumCommentDtoTemp(Comment comment) {
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.memberName = comment.getMember().getMemberName();
    }
}
