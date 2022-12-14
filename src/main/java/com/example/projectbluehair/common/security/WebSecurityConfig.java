package com.example.projectbluehair.common.security;

import com.example.projectbluehair.common.security.exception.CustomAccessDeniedHandler;
import com.example.projectbluehair.common.security.exception.CustomAuthenticationEntryPoint;
import com.example.projectbluehair.common.security.exception.CustomSecurityErrorCode;
import com.example.projectbluehair.common.security.exception.CustomSecurityException;
import com.example.projectbluehair.common.security.jwt.JwtAuthFilter;
import com.example.projectbluehair.common.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration      // Config 파일임을 명시
@EnableWebSecurity  // Spring Security 활성화
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    @Bean // 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().
                requestMatchers(PathRequest.toH2Console()).
                requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF(Cross-site request forgery) 비활성화 설정
        // 공격자가 인증된 브라우저에 저장된 쿠키의 세션 정보를 활용하여 웹 서버에 사용자가 의도하지 않은 요청을 전달하는 것
        http.csrf().disable();

        // Session 방식 비활성화
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // .authorizeRequests() : 요청에 대한 권한을 지정
        http.authorizeRequests().
                // 권한 없이 사용할 파일들 승인, WebSercurityCustomizer로 대체 가능
//                antMatchers("/h2-console/**").permitAll().
//                antMatchers("/css/**").permitAll().
//                antMatchers("/js/**").permitAll().
//                antMatchers("/images/**").permitAll().
                // SignUp, Login api는 인증 없이도 승인
                antMatchers("/signup").permitAll().
                antMatchers("/login").permitAll().
                // .anyRequest().authenticated() : 나머지 모든 Request에 대해, 인증 필요.
                anyRequest().authenticated().
                // JWT Filter 등록
                and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 401 Error, 인증 실패
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        // 403 Error, 권한 오류
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        return http.build();
    }
}
