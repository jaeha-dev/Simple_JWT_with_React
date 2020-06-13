package com.github.devsjh.model.repository;

import com.github.devsjh.model.Role;
import com.github.devsjh.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * @title  : 권한 레포지토리 클래스
 * @author : jaeha-dev (Git)
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleType name);
}