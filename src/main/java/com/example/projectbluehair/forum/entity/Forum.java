package com.example.projectbluehair.forum.entity;

import com.example.projectbluehair.comment.entity.Comment;
import com.example.projectbluehair.forum.dto.ForumSaveRequestDto;
import com.example.projectbluehair.forum.dto.ForumUpdateRequestDto;
import com.example.projectbluehair.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter //수정포인트1
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Forum extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORUM_ID")
    private Long forumId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    //게시글 --N:1--> 회원
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    //게시글 --N:1--> 게시글좋아요
    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ForumLike> forumLikeList = new ArrayList<>();

    //게시글 --N:1--> 댓글
    @OneToMany(mappedBy = "forum")
    private List<Comment> commentList = new ArrayList<>();


    //게시글 작성
    public Forum(ForumSaveRequestDto forumSaveReqDto, Member member) {
        this.title = forumSaveReqDto.getTitle();
        this.content = forumSaveReqDto.getContent();
        this.member = member;
    }

    public Forum(ForumUpdateRequestDto forumUpdateReqDto, Member member) {
        this.title = forumUpdateReqDto.getTitle();
        this.content = forumUpdateReqDto.getContent();
        this.member = member;
    }
}
