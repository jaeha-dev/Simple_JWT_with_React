package com.github.devsjh.service;

import com.github.devsjh.model.Role;
import com.github.devsjh.model.RoleType;
import com.github.devsjh.model.User;
import com.github.devsjh.model.payload.request.JoinRequest;
import com.github.devsjh.model.payload.request.LoginRequest;
import com.github.devsjh.model.payload.response.JwtResponse;
import com.github.devsjh.model.payload.response.MessageResponse;
import com.github.devsjh.model.repository.RoleRepository;
import com.github.devsjh.model.repository.UserRepository;
import com.github.devsjh.security.jwt.JwtUtils;
import com.github.devsjh.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @title  : 인증 서비스 클래스
 * @author : jaeha-dev (Git)
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public ResponseEntity<?> registerUser(JoinRequest joinRequest) {
        if (userRepository.existsByUsername(joinRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("이미 등록된 계정 ID입니다."));
        }
        if (userRepository.existsByEmail(joinRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("이미 등록된 계정 이메일입니다."));
        }

        // 계정 객체 생성
        User user = new User(joinRequest.getUsername(),
                             joinRequest.getEmail(),
                             passwordEncoder.encode(joinRequest.getPassword()));

        Set<String> strRoles = joinRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                                          .orElseThrow(() -> new RuntimeException("Error: User role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                                                       .orElseThrow(() -> new RuntimeException("Error: Admin role is not found."));
                        roles.add(adminRole);
                        break;
                    case "manager":
                        Role managerRole = roleRepository.findByName(RoleType.ROLE_MANAGER)
                                                         .orElseThrow(() -> new RuntimeException("Error: Manager role is not found."));
                        roles.add(managerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                                                      .orElseThrow(() -> new RuntimeException("Error: User role is not found."));
                        roles.add(userRole);
                }
            });
        }

        // 계정 객체에 권한 지정 및 DB 저장
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("계정 등록이 완료되었습니다."));
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwt(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                                                         .map(GrantedAuthority::getAuthority)
                                                         .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                                                 userDetails.getId(),
                                                 userDetails.getUsername(),
                                                 userDetails.getEmail(),
                                                 roles));
    }
}