package com.github.devsjh.model.payload.response;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * @title  : JWT 응답 페이로드 클래스
 * @author : jaeha-dev (Git)
 */
@Getter
@Setter
public class JwtResponse {

    private String accessToken; // 프론트엔드에서 accessToken으로 찾도록 구현하였으므로 이름을 일치시킨다.
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}