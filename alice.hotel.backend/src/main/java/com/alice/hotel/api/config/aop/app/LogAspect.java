package com.alice.hotel.api.config.aop.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.alice.hotel.api.common.GlobalEnum;
import com.alice.hotel.api.config.security.dto.AccountContext;
import com.alice.hotel.api.config.security.service.ApiAuditService;
import com.alice.hotel.api.config.security.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final ApiAuditService apiAuditService;
    /*
      com.admin.service.MemberService의 모든 메서드에 대해 적용한다.
    */
    //@Around("execution(* com.admin.service.MemberService.*(..))")

    /*
      com..service 경로에 있는 모든 ~Service.java의 update~로 시작하는 메서드에 대해 적용한다.
    */
    @Around("execution(* com.alice.hotel.api.domain..*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // Scheduled jobs / non-HTTP calls have no request — do not NPE
        if (attrs == null || attrs.getRequest() == null) {
            return pjp.proceed();
        }

        HttpServletRequest request = attrs.getRequest();
        HttpServletResponse response = attrs.getResponse();

        long startTime = System.currentTimeMillis();
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();
        Object result = null;

        String paramsString = "No arguments";
        String reqPayload = "";
        try {
            paramsString = WebUtil.getArgumentsAsString(pjp.getArgs());
            reqPayload = logRequestPayload(request);
        } catch (Exception e) {
            log.warn("Skip request log: {}", e.getMessage());
        }

        // Never let audit/logging break the business call
        try {
            if (authentication == null || authentication.getName().equals(GlobalEnum.AnomyousUser_String.toString())) {
                System.out.println("==> LogAspect Before:: " + pjp.getSignature().getDeclaringTypeName());
                result = pjp.proceed();
                System.out.println("==> LogAspect After:: " + pjp.getSignature().getDeclaringTypeName());
            } else {
                Object principalObj = authentication.getPrincipal();
                String userLabel = authentication.getName();
                if (principalObj instanceof AccountContext accountContext && accountContext.getUserDTO() != null) {
                    userLabel = accountContext.getUserDTO().getUserId();
                }
                System.out.println(
                        "==> LogAspect Before:: [ " + userLabel + " ]" + pjp.getSignature()
                                .getDeclaringTypeName());
                result = pjp.proceed();
                System.out.println(
                        "==> LogAspect After:: [ " + userLabel + " ]" + pjp.getSignature()
                                .getDeclaringTypeName());
            }
        } catch (Throwable t) {
            throw t;
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("==> Execution Time: {} ms", executionTime);

        // API audit table is optional in Alice demo — never block business APIs
        try {
            if (authentication != null) {
                String resPayload = logResponsePayload(result);
                saveLogToDatabase(request, response, request.getMethod(), paramsString,
                        executionTime, reqPayload, resPayload, authentication);
            }
        } catch (Exception e) {
            log.warn("Skip API audit: {}", e.getMessage());
        }

        return result;
    }

    private void saveLogToDatabase(HttpServletRequest request, HttpServletResponse response,
            String methodName, String params, long executionTime, String reqPayload,
            String resPayload, Authentication authentication) {

        if (authentication.getName().equals(GlobalEnum.AnomyousUser_String.toString())) {
            apiAuditService.saveAnonymousLog(request, response, methodName, params, executionTime, reqPayload,
                    resPayload);
        } else {
            AccountContext accountContext = (AccountContext) authentication.getPrincipal();
            apiAuditService.logApiUsageUser(request, response, methodName, params, executionTime, reqPayload,
                    resPayload,
                    accountContext.getUserDTO().getUserId());
        }
    }

    private String logRequestPayload(HttpServletRequest request) {
        StringBuilder payload = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                payload.append(line);
            }
        } catch (Exception e) {
            log.error("Error logging request payload: {}", e.getMessage());
        }
        return payload.toString();
    }

    private String logResponsePayload(Object response) {
        try {
            String resPayload = "";
            if (response != null) {
                return objectMapper.writeValueAsString(response);
            }
            return resPayload;
        } catch (Exception e) {
            log.error("Error logging response payload: {}", e.getMessage());
            return "";
        }
    }
}
