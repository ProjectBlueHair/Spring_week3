package com.example.projectbluehair.forum.entity;

import com.example.projectbluehair.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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
    @OneToMany
    @JoinColumn(name = "FORUM_ID")
    private List<ForumLike> forumLikeList = new ArrayList<>();
}
