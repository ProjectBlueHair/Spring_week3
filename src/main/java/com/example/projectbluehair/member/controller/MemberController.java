package com.example.projectbluehair.member.controller;

import javax.servlet.http.HttpServletResponse;

import com.example.projectbluehair.member.dto.RegisterRequestDto;
import com.example.projectbluehair.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody RegisterRequestDto requestDto) {

        return memberService.signup(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto loginRequestDto, HttpServletResponse response) throws NoSuchAlgorithmException {
        return userService.login(loginRequestDto, response);
    }
}
