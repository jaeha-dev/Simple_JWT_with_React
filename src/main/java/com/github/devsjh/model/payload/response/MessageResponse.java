package com.github.devsjh.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @title  : 응답 메시지 페이로드 클래스
 * @author : jaeha-dev (Git)
 */
@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {

    private String message;
}