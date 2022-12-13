package com.example.projectbluehair.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpDto {
    private String memberName;
    private String password;
    private boolean admin = false;
    private String adminToken = "";

    @Builder
    public SignUpDto(String memberName, String password, boolean admin, String adminToken) {
        this.memberName = memberName;
        this.password = password;
        this.admin = admin;
        this.adminToken = adminToken;
    }
}
