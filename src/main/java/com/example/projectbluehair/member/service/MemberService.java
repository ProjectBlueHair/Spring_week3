package com.example.projectbluehair.member.service;

import com.example.projectbluehair.common.security.jwt.JwtUtil;
import com.example.projectbluehair.member.dto.SignUpDto;
import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.dto.LoginDto;
import com.example.projectbluehair.member.dto.LoginResponseDto;
import com.example.projectbluehair.member.dto.SignUpResponseDto;
import com.example.projectbluehair.member.entity.MemberMapper;
import com.example.projectbluehair.member.entity.MemberRole;
import com.example.projectbluehair.common.exception.CustomException;
import com.example.projectbluehair.common.exception.CommonErrorCode;
import com.example.projectbluehair.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:auth.properties")
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    private final JwtUtil jwtUtil;
    private final Validator validator;

    @Value("${admin.token}")
    String ADMIN_TOKEN;

    @Transactional
    public SignUpResponseDto signUp(SignUpDto dto) {
        // Entity Member에 포함되어야 할 Data들
        String memberName = dto.getMemberName();
        String password = dto.getPassword();
        MemberRole role = MemberRole.USER;

        // 1. MemberName 확인
        // 1-1. MemberName 형식 확인
        if(!validator.isValidMemberName(memberName)){
            throw new CustomException(CommonErrorCode.INVALID_MEMBERNAME);
        }
        // 1-2. MemberName 중복 확인
        memberRepository.findByMemberName(memberName)
                .ifPresent(m-> {
                    throw new CustomException(CommonErrorCode.DUPLICATE_MEMBERNAME);
                });

        // 2. Password 확인
        // 2-1. Password 형식 확인
        if(!validator.isValidPassword(password)){
            throw new CustomException(CommonErrorCode.INVALID_PASSWORD);
        }
        // 2-2. Password 암호화
        password = passwordEncoder.encode(password);

        // 3. 사용자 ROLE 확인
        if(dto.isAdmin()) {
            if(!dto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(CommonErrorCode.INVALID_AUTH_TOKEN);
            }
            // 3-1. 토큰 일치할 경우 ADMIN 부여
            role = MemberRole.ADMIN;
        }

        // 4. Entity Member 생성 및 DB 저장 (Mapper 사용)
        Member member = memberMapper.toEntity(memberName, password, role);
        memberRepository.save(member);

        // 5. SignUpResponseDto 반환
        return new SignUpResponseDto(member);
    }

    @Transactional
    public LoginResponseDto login(LoginDto dto, HttpServletResponse response) {
        // Login DTO에서 확인해야 할 Data들
        String memberName = dto.getMemberName();
        String password = dto.getPassword();

        // 1. 사용자 존재 여부 확인
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(()-> new CustomException(CommonErrorCode.MEMBER_NOT_FOUND));

        // 2. 비밀번호 일치 여부 확인
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new CustomException(CommonErrorCode.INCORRECT_PASSWORD);
        }

        // 3. Token 생성
        String accessToken = jwtUtil.createAccessToken(member.getMemberName(), member.getRole());
        String refreshToken = jwtUtil.createRefreshToken();

        // 4. Token 발급
        response.addHeader(JwtUtil.AUTHORIZATION_ACCESS, accessToken);
        response.addHeader(JwtUtil.AUTHORIZATION_REFRESH, refreshToken);
        
        // 5. 발급된 Token DB에 저장
        member.updateToken(accessToken, refreshToken);

        return new LoginResponseDto(member);
    }

}
