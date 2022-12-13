package com.example.projectbluehair.forum.dto;

import com.example.projectbluehair.comment.entity.Comment;
import com.example.projectbluehair.forum.entity.Forum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ForumResponseDto {
    private Long forumId;
    private String title;
    private String memberName;
    private String content;
    private LocalDateTime createdAt;
    private Long liekCount;
    private boolean liked = false;

    private List<Comment> commentList; //<<수정포인트1>> comment부분 수정 필요

    public ForumResponseDto(Forum forum, Long likeCount) {
        this.forumId = forum.getForumId();
        this.title = forum.getTitle();
        this.memberName = forum.getMember().getMemberName();
        this.content = forum.getContent();
        this.createdAt = forum.getCreatedAt();
        this.liekCount = 0L; //최초 게시글 좋아요 0
        this.commentList = forum.getCommentList(); //<<수정포인트1>>
    }


    public ForumResponseDto(Forum forum, Long likeCount, boolean liked) {
        this.forumId = forum.getForumId();
        this.title = forum.getTitle();
        this.memberName = forum.getMember().getMemberName();
        this.content = forum.getContent();
        this.createdAt = forum.getCreatedAt();
        this.liekCount = likeCount;
        this.liked = liked;
        this.commentList = forum.getCommentList();
    }
}
