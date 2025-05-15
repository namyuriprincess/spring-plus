package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminAccessLoggingAspect {

    private final HttpServletRequest request;

    @Around("execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public void logWhenChangeUserRole(JoinPoint joinPoint) {
        String userId = String.valueOf(request.getAttribute("userId"));
        String requestUri = request.getRequestURI();
        String requestTime = LocalDateTime.now().toString();

        log.info("Admin Access Log - User ID: {}, Request Time: {}, Request URI: {}, Method: {}",
                userId, requestTime, requestUri, request.getMethod());
    }
}
