package com.example.projectbluehair.comment.entity;

import com.example.projectbluehair.forum.entity.Forum;
import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.entity.TimeStamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Comment extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    @Column(nullable = false)
    private String content;

    //댓글 --N:1--> 회원
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    //댓글 --N:1--> 게시글
    @ManyToOne
    @JoinColumn(name = "FORUM_ID", nullable = false)
    private Forum forum;

    //댓글 --1:N--> 댓글좋아요
    @OneToMany
    @JoinColumn(name = "COMMENT_ID")
    private List<CommentLike> commentLikeList = new ArrayList<>();

    //자기참조 : 부모댓글 <--1:N--> 자식댓글
    @ManyToOne  //자식입장(연관관계 주인)
    @JoinColumn(name = "PARENT_COMMENT_ID")
    private Comment parentCommentId;

    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL) //부모입장
    private List<Comment> commentList = new ArrayList<>();

    //댓글의 댓글.
    public Comment(String content, Comment comment2, Member member, Forum forum) {
        this.content = content;
        this.member = member;
        this.forum = forum;
        this.parentCommentId = comment2;
    }

    //댓글
    public Comment(String content, Member member, Forum forum) {
        this.content = content;
        this.member = member;
        this.forum = forum;
    }

    public void update(String content) {
        this.content = content;
    }






    /*public Comment(String content, Member member, Forum forum, Comment parentCommentId) {
        this.content = content;
        this.member = member;
        this.forum = forum;
        this.parentCommentId = parentCommentId; //모르겠다.
    }*/
}
