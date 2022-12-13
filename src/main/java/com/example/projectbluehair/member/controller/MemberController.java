package com.example.projectbluehair.member.controller;

import com.example.projectbluehair.common.response.ResponseDto;
import com.example.projectbluehair.common.response.SuccessResponse;
import com.example.projectbluehair.member.dto.LoginRequestDto;
import com.example.projectbluehair.member.dto.SignUpRequestDto;
import com.example.projectbluehair.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final SuccessResponse successResponse;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        // 1. SignUp 서비스 실행 (SignUpResponseDto 반환)
        // 2. ResponseDto에 SignUpResponseDto 넣기
        return successResponse.respond(HttpStatus.OK, "회원 가입 완료",
                memberService.signUp(requestDto.toSignUpDto()));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto requestDto,
                                                  HttpServletResponse response) {
        // 1. Login 서비스 실행 (LoginResponseDto 반환)
        // 2. ResponseDto에 LoginResponseDto 넣기
        return successResponse.respond(HttpStatus.OK, "로그인 완료",
                memberService.login(requestDto.toLoginDto(), response));
    }
}
