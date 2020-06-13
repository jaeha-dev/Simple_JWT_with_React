package com.github.devsjh.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @title  : 가입 요청 페이로드 클래스
 * @author : jaeha-dev (Git)
 */
@Getter
@Setter
public class JoinRequest {

    @NotBlank @Size(min = 5, max = 20)
    private String username;

    @NotBlank @Email @Size(max = 50)
    private String email;

    @NotBlank @Size(min = 5, max = 20)
    private String password;

    private Set<String> role;
}