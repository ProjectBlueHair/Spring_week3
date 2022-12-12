package com.example.projectbluehair.member.dto;

import lombok.Getter;

@Getter
public class RegisterRequestDto {
    private String memberName;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
