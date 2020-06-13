package com.github.devsjh.web;

import com.github.devsjh.model.payload.request.JoinRequest;
import com.github.devsjh.model.payload.request.LoginRequest;
import com.github.devsjh.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @title  : 인증 컨트롤러 클래스
 * @author : jaeha-dev (Git)
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<?> registerUser(@Valid @RequestBody JoinRequest joinRequest) {
        log.info("계정 등록 ID: {}", joinRequest.getUsername());
        return authService.registerUser(joinRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("계정 로그인 ID: {}", loginRequest.getUsername());
        return authService.authenticateUser(loginRequest);
    }
}