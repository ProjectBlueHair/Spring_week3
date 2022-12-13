package com.example.projectbluehair.member.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String memberName;
    private String password;
    private boolean admin = false;
    private String adminToken = "";

    // Builder를 통해 RequestDto를 서비스 Dto로 변경
    public SignUpDto toSignUpDto(){
        return SignUpDto.builder()
                .memberName(memberName)
                .password(password)
                .admin(admin)
                .adminToken(adminToken)
                .build();
    }
}
