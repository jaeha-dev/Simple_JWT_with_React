package com.github.devsjh.model.repository;

import com.github.devsjh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * @title  : 계정 레포지토리 클래스
 * @author : jaeha-dev (Git)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // 로그인
    Boolean existsByUsername(String username); // 중복 검사
    Boolean existsByEmail(String email); // 중복 검사
}