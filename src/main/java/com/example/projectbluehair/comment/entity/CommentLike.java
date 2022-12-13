package com.example.projectbluehair.comment.entity;

import com.example.projectbluehair.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_LIKE_ID")
    private Long commentLikeId;

    //댓글좋아요 --N:1--> 회원
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    //댓글좋아요 --N:1--> 댓글
    @ManyToOne
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    private Comment comment;
}
