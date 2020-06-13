package com.github.devsjh.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @title  : 로그 기능 클래스
 * @author : jaeha-dev (Git)
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 메소드 실행 전/후 로깅 수행
     */
    @Around("execution(* com.github.devsjh.web..*.*(..))")
    // @Around("execution(* com.github.devsjh..*.*(..))")
    public Object logging(ProceedingJoinPoint point) throws Throwable {
        log.info("◆ start: " + point.getSignature().getDeclaringTypeName() + " / " + point.getSignature().getName());

        // proceed() 메소드는 타겟 메소드를 지칭하며 이 메소드를 실행해야만 타켓 메소드가 수행된다.
        // 그리고 타켓 메소드의 반환 값(result)를 반환한다.
        Object result = point.proceed();
        log.info("◆ finished: " + point.getSignature().getDeclaringTypeName() + " / " + point.getSignature().getName());

        return result;
    }
}