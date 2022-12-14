package com.example.projectbluehair.common.security.jwt;

import com.example.projectbluehair.common.security.exception.CustomSecurityErrorResponse;
import com.example.projectbluehair.common.security.exception.CustomSecurityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomSecurityException ex){
            log.error("JWT 인증/인가 관련 이슈");
            setErrorResponse(response, ex);
        }
    }

    private void setErrorResponse(HttpServletResponse response,
                                  CustomSecurityException ex) throws IOException{
        // Json Type으로 반환할 것 명시
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // HttpStatus 설정
        response.setStatus(ex.getErrorCode().getHttpStatus().value());
        // CustomErrorResponse 생성
        CustomSecurityErrorResponse customSecurityErrorResponse =
                new CustomSecurityErrorResponse(
                        ex.getErrorCode().getHttpStatus().value(),
                        ex.getErrorCode().getHttpStatus().name(),
                        ex.getErrorCode().name(),
                        ex.getErrorCode().getDetail());

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, customSecurityErrorResponse);
            os.flush();
        }
    }
}
