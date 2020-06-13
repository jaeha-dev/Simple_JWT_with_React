package com.github.devsjh.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.devsjh.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @title  : 계정 모델 클래스 (for Spring Security)
 * @author : jaeha-dev (Git)
 */
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private final String password; // 계정 비밀번호
    private final Long id; // 계정 고유 ID
    private final String username; // 계정 ID
    private final String email; // 계정 이메일
    private final Collection<? extends GrantedAuthority> authorities; // 계정 권한

    /**
     * 생성자
     * @param id          : 계정 고유 ID
     * @param username    : 계정 ID
     * @param email       : 계정 이메일
     * @param password    : 계정 비밀번호
     * @param authorities : 계정 권한
     */
    public CustomUserDetails(Long id,
                             String username,
                             String email,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * User 객체로 CustomUserDetails 객체를 생성한다.
     * @param user : User 객체
     * @return     : CustomUserDetails 객체
     */
    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                                                            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                                                            .collect(Collectors.toList());
        return new CustomUserDetails(user.getId(),
                                     user.getUsername(),
                                     user.getEmail(),
                                     user.getPassword(),
                                     authorities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CustomUserDetails userDetails = (CustomUserDetails) o;
        return Objects.equals(id, userDetails.id);
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}