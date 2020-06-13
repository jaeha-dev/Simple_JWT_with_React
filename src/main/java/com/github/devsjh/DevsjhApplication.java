package com.github.devsjh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @title  : Spring Boot JWT with React
 * @author : jaeha-dev (Git)
 * @refer  : https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication
 * @memo   : 로깅을 위한 AOP 적용
 * (EnableAspectJAutoProxy: AOP를 찾을 수 있게 한다.)
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class DevsjhApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevsjhApplication.class, args);
    }
}