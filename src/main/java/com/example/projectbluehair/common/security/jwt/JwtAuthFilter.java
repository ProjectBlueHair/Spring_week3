package com.example.projectbluehair.common.security.jwt;

import com.example.projectbluehair.common.security.exception.CustomAuthenticationEntryPoint;
import com.example.projectbluehair.common.security.exception.CustomSecurityException;
import com.example.projectbluehair.common.security.exception.CustomSecurityErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWTAuthFilter Called");
        // Login, SignUp 기능의 경우 해당 Filter 건너뜀.
        String uri = request.getRequestURI();
        if (uri.equals("/login") || uri.equals("/signup") || uri.equals("/forum")){
            System.out.println("JWT Filter Skipped");
            filterChain.doFilter(request, response);
            return;
        }

        // Forum 하나를 확인할 때 Filter 건너뛰기 위함
        // /forum/ 뒤에 오는 문자열이 숫자로만 구성되어있는지(forum id) 아닌지 판단
        if (uri.contains("/forum")){
            String[] strarr = uri.split("/");
            char[] chararr = strarr[strarr.length - 1].toCharArray();
            boolean flag = false;
            for (int i = 0; i < chararr.length; i++) {
                if (chararr[i] < '0' || chararr[i] > '9'){
                    flag = true;
                }
            }
            if (!flag){
                System.out.println("JWT Filter Skipped");
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 1. Request에서 토큰 추출
        String token = jwtUtil.resolveToken(request);

        // 2. Token 유효성 검사 및 인증
        // 2-1. Token 존재 여부 확인
        if(token == null) {
            System.out.println("There is no Token");
            throw new CustomSecurityException(CustomSecurityErrorCode.JWT_NOT_FOUND);
        }
        // 2-2. Token 유효성 확인
        if(!jwtUtil.validateToken(token)){
            System.out.println("Token is invalid");
            throw new CustomSecurityException(CustomSecurityErrorCode.INVALID_JWT);
        }
        // 3. 사용자 인증
        Claims info = jwtUtil.getUserInfoFromHttpServletRequest(request);
        setAuthentication(info.getSubject());

        // 4. 다음 필터로 보냄
        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String memberName) {
        // 1. Security Context 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // 2. Authentication 생성
        Authentication authentication = jwtUtil.createAuthentication(memberName);
        // 3. Context에 Authentication 넣기
        context.setAuthentication(authentication);
        // 4. Security Context Holder에 Context 넣기
        SecurityContextHolder.setContext(context);
    }
}