package com.example.projectbluehair.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginDto {
    private String memberName;
    private String password;

    @Builder
    public LoginDto(String memberName, String password) {
        this.memberName = memberName;
        this.password = password;
    }
}
