package com.github.devsjh.security.jwt;

import com.github.devsjh.security.service.CustomUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;

/**
 * @title  : JWT 유틸리티 컴포넌트 클래스
 * @author : jaeha-dev (Git)
 */
@Component
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${devsjh.app.jwtSecret}")
    private String jwtSecret;

    @Value("${devsjh.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * JWT 생성
     * @param authentication : 계정 정보
     * @return               : JWT
     */
    public String generateJwt(Authentication authentication) {
        log.info("generateJwt()");
        log.info("Key: {}", jwtSecret);
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
        log.info("ID: {}", userPrincipal.getUsername());

        // jwt secret key byte array cannot be null or empty 에러
        // https://stackoverflow.com/questions/60275087/java-lang-illegalargumentexception-secret-key-byte-array-cannot-be-null-or-empt
        String encodedString = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        String jwt = Jwts.builder().setSubject((userPrincipal.getUsername()))
                                   .setIssuedAt(new Date())
                                   .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                                   .signWith(SignatureAlgorithm.HS512, encodedString)
                                   .compact(); // 토큰 생성

        log.info("JWT: {}", jwt);
        return jwt;
    }

    /**
     * JWT 파싱
     * @param token : JWT
     * @return      : 계정 ID
     */
    public String getUsernameFromJwt(String token) {
        log.info("getUsernameFromJwt()");
        log.info("Key: {}", jwtSecret);
        log.info("Token: {}", token);

        // A signing key must be specified if the specified JWT is digitally signed 에러
        // https://stackoverflow.com/questions/41661821/java-lang-illegalargumentexception-a-signing-key-must-be-specified-if-the-speci
        String encodedString = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        return Jwts.parser().setSigningKey(encodedString)
                            .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * JWT 검증
     * @param token : JWT
     * @return      : 검증 결과
     */
    public boolean validateJwt(String token) {
        log.info("validateJwt()");
        log.info("Key: {}", jwtSecret);
        log.info("Token: {}", token);
        try {
            // A signing key must be specified if the specified JWT is digitally signed 에러
            // https://stackoverflow.com/questions/41661821/java-lang-illegalargumentexception-a-signing-key-must-be-specified-if-the-speci
            String encodedString = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
            Jwts.parser().setSigningKey(encodedString).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("잘못된 JWT 서명: {}" + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("잘못된 JWT 전달: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 전달: {}" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 형식: {}" + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("빈 JWT 전달: {}" + e.getMessage());
        }

        return false;
    }
}