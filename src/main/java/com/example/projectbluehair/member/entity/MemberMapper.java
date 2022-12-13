package com.example.projectbluehair.member.entity;

import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toEntity(String memberName, String password, MemberRole role) {
        return new Member(memberName, password, role);
    }
}
