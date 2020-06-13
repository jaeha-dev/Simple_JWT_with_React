package com.github.devsjh.security.jwt;

import com.github.devsjh.security.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title  : 인증 토큰 필터 클래스
 * @author : jaeha-dev (Git)
 * @memo   : OncePerRequestFilter (요청 시, 필터가 한 번만 수행된다.)
 * (https://stackoverflow.com/questions/13152946/what-is-onceperrequestfilter)
 */
public class AuthTokenFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private JwtUtils jwtUtils;

    /**
     * 인증 처리를 위한 요청 필터링
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            log.info("doFilterInternal()");
            log.info("JWT: {}", jwt);

            if (jwt != null && jwtUtils.validateJwt(jwt)) {
                String username = jwtUtils.getUsernameFromJwt(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // UsernamePasswordAuthenticationToken: Authentication 인터페이스의 구현체이다.
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception e) {
            log.error("계정 인증 불가: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * JWT 추출
     * @param request : 요청
     * @return        : JWT
     */
    private String parseJwt(HttpServletRequest request) {
        log.info("parseJwt()");
        // Authorization: Bearer [header].[payload].[signature]
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            // [header].[payload].[signature]
            return authHeader.substring(7, authHeader.length());
        }

        return null;
    }
}