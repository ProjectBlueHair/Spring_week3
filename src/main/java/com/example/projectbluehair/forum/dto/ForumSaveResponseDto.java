package com.example.projectbluehair.forum.dto;

import com.example.projectbluehair.comment.entity.Comment;
import com.example.projectbluehair.forum.entity.Forum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ForumSaveResponseDto {
    private Long forumId;
    private String title;
    private String memberName;
    private String content;
    private LocalDateTime createdAt;
    private int liekCount;
    private boolean liked;

    @JsonIgnore
    private List<Comment> commentList = new ArrayList<>(); //<<참고포인트>>

    public ForumSaveResponseDto(Forum forum) {
        this.forumId = forum.getForumId();
        this.title = forum.getTitle();
        this.memberName = forum.getMember().getMemberName();
        this.content = forum.getContent();
        this.createdAt = forum.getCreatedAt();
        this.liekCount = 0; //최초 게시글 좋아요 0
        this.liked = false; //최초 게시글 좋아요 이력 false
        //this.commentList = new ArrayList<>(); //<<참고포인트>>
    }

}
