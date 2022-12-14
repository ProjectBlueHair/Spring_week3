package com.example.projectbluehair.member.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String memberName;
    private String password;

    // Builder를 통해 RequestDto를 서비스 Dto로 변경
    public LoginDto toLoginDto(){
        return LoginDto.builder()
                .memberName(memberName)
                .password(password)
                .build();
    }
}
