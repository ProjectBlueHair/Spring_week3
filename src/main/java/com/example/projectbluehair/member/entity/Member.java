package com.example.projectbluehair.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "MEMBER_ID") //임시로 만듬 내가. 삭제해야될꺼0
    private Long id;

    //임시로 만듬 내가. 삭제해야될꺼.
   // @OneToMany(mappedBy = "member")
    //private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String memberName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String accessToken = "";

    @Column(nullable = false)
    private String refreshToken = "";

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    public Member(String memberName, String password, MemberRole role) {
        this.memberName = memberName;
        this.password = password;
        this.role = role;
    }

    public void updateToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    //    @OneToMany(mappedBy = "member")
//    private List<Forum> forumList = new ArrayList<>();

}

