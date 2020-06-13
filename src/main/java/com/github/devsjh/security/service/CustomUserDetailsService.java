package com.github.devsjh.security.service;

import com.github.devsjh.model.User;
import com.github.devsjh.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title  : 계정 서비스 클래스 (for Spring Security)
 * @author : jaeha-dev (Git)
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * 데이터베이스에서 계정 ID를 조회한다.
     * (비밀번호 검증은 AuthenticationProvider 구현체에서 수행한다.)
     * (구현체에서 authenticate() 메소드를 통해 Authentication 객체(UsernamePasswordAuthentication)를 반환한다.)
     * @param username                   : 계정 ID
     * @return UserDetails               : 계정 모델 객체
     * @throws UsernameNotFoundException : 계정 ID가 존재하지 않을 때 예외
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정 ID: " + username));

        return CustomUserDetails.build(user);
    }
}