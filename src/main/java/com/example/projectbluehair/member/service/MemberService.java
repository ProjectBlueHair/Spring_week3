package com.example.projectbluehair.member.service;

import com.example.post_api.dto.*;
import com.example.post_api.entity.User;
import com.example.post_api.entity.UserRoleEnum;
import com.example.post_api.exception.CustomException;
import com.example.post_api.exception.ErrorCode;
import com.example.post_api.jwt.JwtUtil;
import com.example.post_api.repository.UserRepository;
import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:auth.properties")
public class MemberService {
    private final MemberRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${admin.token}")
    String ADMIN_TOKEN;

    @Transactional
    public ResponseEntity<UserRegiResponseDto> register(UserRegiRequestDto requestDto) throws NoSuchAlgorithmException {
        // 객체 User에 포함되어야 할 Data들
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        UserRoleEnum role = UserRoleEnum.USER;

        // 1. username 확인
        // 1-1. username 형식 확인
        if(!Validator.isValidUsername(username)){
            throw new CustomException(ErrorCode.INVALID_USERNAME);
        }
        // 1-2. username 중복 확인
        userRepository.findByUsername(username)
                .ifPresent(m-> {
                    throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
                });

        // 2. password 확인
        // 2-1. password 형식 확인
        if(!Validator.isValidPassword(password)){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        // 2-2. password 암호화(SHA256)
        password = SHA256.encrypt(password);

        // 3. 사용자 Role 확인
        if(requestDto.isAdmin()) {
            if(!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
            }
            // 3-1. 토큰 일치할 경우 ADMIN 부여
            role = UserRoleEnum.ADMIN;
        }

        // User Entity 생성 및 DB 저장
        User user = new User(username, password, role);
        userRepository.save(user);

        // UserRegiResponseDto 생성
        UserRegiResponseDto userRegiResponseDto = new UserRegiResponseDto(user);

        // header 생성
        HttpHeaders header = new HttpHeaders();
        header.add("msg", "Register Completed");
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(userRegiResponseDto, header, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<UserLoginResponseDto> login(UserLoginRequestDto requestDto, HttpServletResponse response) throws NoSuchAlgorithmException {
        // Login Request에서 확인해야할 Data들
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 1. 사용자 존재 여부 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 비밀번호 일치 여부 확인
        if(!user.getPassword().equals(SHA256.encrypt(password))){
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        // header 생성
        HttpHeaders header = new HttpHeaders();
        header.add("msg", "Login Success");
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(new UserLoginResponseDto(user), header, HttpStatus.OK);
    }
}
