package com.example.projectbluehair.common.security.jwt;

import com.example.projectbluehair.common.security.UserDetailsServiceImpl;
import com.example.projectbluehair.common.security.exception.CustomSecurityException;
import com.example.projectbluehair.common.security.exception.CustomSecurityErrorCode;
import com.example.projectbluehair.member.entity.Member;
import com.example.projectbluehair.member.entity.MemberRole;
import com.example.projectbluehair.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component // Bean 등록(의존성 주입 위해서 사용)
@PropertySource("classpath:auth.properties")
@RequiredArgsConstructor
public class JwtUtil {
    private final MemberRepository memberRepository;
    // Header의 AccessToken Key 값
    public static final String AUTHORIZATION_ACCESS = "AccessToken";
    // Header의 RefreshToken Key 값
    public static final String AUTHORIZATION_REFRESH = "RefreshToken";

    // 사용자 권한 값의 Key
    public static final String AUTHORIZATION_KEY = "auth";
    private final UserDetailsServiceImpl userDetailsService;
    // 토큰 생성 시 앞에 붙는 식별자
    private static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료 시간
    private static final long TOKEN_TIME = 30 * 1000L;

    // JWT SecretKey 불러오기
    @Value("${jwt.secret.key.access}")
    private String accessTokenSecretKey;
    @Value("${jwt.secret.key.refresh}")
    private String refreshTokenSecretKey;
    private Key accessTokenkey;
    private Key refreshTokenkey;

    // 사용할 암호화 알고리즘 설정
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 초기화 하는 부분
    @PostConstruct
    public void init() {
        byte[] accessTokenBytes = Base64.getDecoder().decode(accessTokenSecretKey); // JWT 토큰 값을 Base64 형식을 디코딩
        accessTokenkey = Keys.hmacShaKeyFor(accessTokenBytes); // key에 넣어줌

        byte[] refreshTokenBytes = Base64.getDecoder().decode(refreshTokenSecretKey); // JWT 토큰 값을 Base64 형식을 디코딩
        refreshTokenkey = Keys.hmacShaKeyFor(refreshTokenBytes); // key에 넣어줌
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request, String authorization) {
        String bearerToken = request.getHeader(authorization); // AccessToken의 value를 가지고옴
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) { // Bearer 값 있는지 확인
            return bearerToken.substring(7); // 확인되면 Bearer 빼고 반환
        }
        return null;
    }

    // Access Token 생성
    public String createAccessToken(String username, MemberRole role) {
        Date date = new Date();

        return BEARER_PREFIX + // BEARER 앞에 붙여주기
                Jwts.builder()
                        .setSubject(username) // subject 부분에 username 넣기
                        .claim(AUTHORIZATION_KEY, role) // claim 부분에 사용자 권한 키와 역할 넣기
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 언제 생성되었는지
                        .signWith(accessTokenkey, signatureAlgorithm) // Key를 어떻게 암호화 할 것인지
                        .compact();
    }
    // Refresh Token 생성
    public String createRefreshToken() {
        Date date = new Date();
        return BEARER_PREFIX + // BEARER 앞에 붙여주기
                Jwts.builder()
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME + 60 * 30 * 1000L)) // 만료 시간(30분)
                        .setIssuedAt(date) // 언제 생성되었는지
                        .signWith(refreshTokenkey, signatureAlgorithm) // Key를 어떻게 암호화 할 것인지
                        .compact();
    }

    // 토큰 검증
    public boolean validateAccessToken(String accessToken, HttpServletRequest request, HttpServletResponse response) {
        try {
            Jwts.parserBuilder().setSigningKey(accessTokenkey).build().parseClaimsJws(accessToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid Access JWT signature, 유효하지 않는 Access JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            // 만료된 경우 토큰 재발급
            log.info("Expired Access JWT, 만료된 Access JWT 입니다.");
            accessTokenReissuance(request, response);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported Access JWT, 지원되지 않는 Access JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("Access JWT claims is empty, 잘못된 Access JWT 입니다.");
        }
        return false;
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder().setSigningKey(refreshTokenkey).build().parseClaimsJws(refreshToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid Refresh JWT signature, 유효하지 않는 Refresh JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired Refresh JWT, 만료된 Refresh JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported Refresh JWT, 지원되지 않는 Refresh JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("Refresh JWT claims is empty, 잘못된 Refresh JWT 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromHttpServletRequest(HttpServletRequest request) throws CustomSecurityException {
        // Request에서 Token 가져오기
        String token = resolveToken(request, AUTHORIZATION_ACCESS);

        // Token이 있는지 확인
        if (token == null) {
            throw new CustomSecurityException(CustomSecurityErrorCode.JWT_NOT_FOUND);
        }

        // Token 유효성 판단(만료, 지원, 형식 등) 및  사용자 정보 가져오기
//        if (validateToken(token, request, response)){
//            // 사용자 정보 반환 (Claims)
//            return Jwts.parserBuilder().setSigningKey(accessTokenkey).build().parseClaimsJws(token).getBody();
//        }
//        else {
//            throw new CustomSecurityException(CustomSecurityErrorCode.INVALID_JWT);
//        }
        try {
            return Jwts.parserBuilder().setSigningKey(accessTokenkey).build().parseClaimsJws(token).getBody();
        }
        // Exception 발생 시 Exception에서 그냥 빼옴
        catch(ExpiredJwtException ex){
            return ex.getClaims();
        }
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String memberName) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberName); // 이름을 통해 사용자 조회
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //userDetail 및 권한 넣어 생성
    }

    private void accessTokenReissuance(HttpServletRequest request, HttpServletResponse response) {
        // 1. request에서 accessToken 분해 후, 사용자 이름 찾아옴.
        Claims info = getUserInfoFromHttpServletRequest(request);
        System.out.println("username : " + info.getSubject());
        // 2. 사용자 이름을 통해 Member Entity 불러옴
        Member member = memberRepository.findByMemberName(info.getSubject())
                .orElseThrow(()-> new CustomSecurityException(CustomSecurityErrorCode.INVALID_JWT));
        // 3. MemberEntity에 등록된 Access Token과 Refresh Token이 Request에 담긴 값과 일치하는 지 확인
        if (member.getAccessToken().substring(7).equals(resolveToken(request, "AccessToken"))
                && member.getRefreshToken().substring(7).equals(resolveToken(request, "RefreshToken"))){
            // 4. RefreshToken 유효성 검사
            if (validateRefreshToken(resolveToken(request, "RefreshToken"))){
                System.out.println("JWT 재발급 조건 충족");
                String newAccessToken = createAccessToken(member.getMemberName(), member.getRole());
                response.addHeader(JwtUtil.AUTHORIZATION_ACCESS, newAccessToken);
                // 5. Member Data 최신화
                member.updateToken(newAccessToken, member.getRefreshToken());
                memberRepository.save(member);
            }
        }
        // 6. 로그인 페이지 반환
    }
}
