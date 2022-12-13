package com.example.projectbluehair.member.controller;

import com.example.projectbluehair.member.dto.LoginRequestDto;
import com.example.projectbluehair.member.dto.LoginResponseDto;
import com.example.projectbluehair.member.dto.SignUpRequestDto;
import com.example.projectbluehair.member.dto.SignUpResponseDto;
import com.example.projectbluehair.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        // 1. SignUp 서비스 실행 (SignUpResponseDto 반환)
        // 2. ResponseDto에 SignUpResponseDto 넣기 -> 민호님 객체 만드시면 수정
        // 3. Response Entity에 담아서 반환
        return new ResponseEntity<>(memberService.signUp(requestDto.toSignUpDto()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto,
                                                  HttpServletResponse response) {
        // 1. Login 서비스 실행 (LoginResponseDto 반환)
        // 2. ResponseDto에 LoginResponseDto 넣기 -> 민호님 객체 만드시면 수정
        // 3. Response Entity에 담아서 반환
        return new ResponseEntity<>(memberService.login(requestDto.toLoginDto(), response), HttpStatus.OK);
    }
}
