package com.github.devsjh.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title  : 테스트 컨트롤러 클래스
 * @author : jaeha-dev (Git)
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestAuthController {
    private static final Logger log = LoggerFactory.getLogger(TestAuthController.class);

    @GetMapping("/all")
    public String allAccess() {
        log.info("/api/test/all");
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        log.info("/api/test/user");
        return "User Content.";
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public String managerAccess() {
        log.info("/api/test/manager");
        return "Manager Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        log.info("/api/test/admin");
        return "Admin Content.";
    }
}