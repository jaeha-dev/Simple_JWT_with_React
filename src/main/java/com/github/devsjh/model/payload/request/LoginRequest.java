package com.github.devsjh.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

/**
 * @title  : 로그인 요청 페이로드 클래스
 * @author : jaeha-dev (Git)
 */
@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}