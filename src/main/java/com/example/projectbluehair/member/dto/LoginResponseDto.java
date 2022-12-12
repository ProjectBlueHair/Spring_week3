package com.example.projectbluehair.member.dto;

import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.entity.MemberRole;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String memberName;
    private MemberRole role;

    public LoginResponseDto(Member member) {
        this.memberName = member.getMemberName();
        this.role = member.getRole();
    }
}
