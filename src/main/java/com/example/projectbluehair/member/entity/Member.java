package com.example.projectbluehair.member.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Member extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    public Member(String memberName, String password, MemberRole role) {
        this.memberName = memberName;
        this.password = password;
        this.role = role;
    }

    //    @OneToMany(mappedBy = "member")
//    private List<Forum> forumList = new ArrayList<>();

}

