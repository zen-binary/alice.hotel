package com.winwintek.web.backend.api.config.aop.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winwintek.web.backend.api.common.GlobalEnum;
import com.winwintek.web.backend.api.config.security.dto.AccountContext;
import com.winwintek.web.backend.api.config.security.service.ApiAuditService;
import com.winwintek.web.backend.api.config.security.util.WebUtil;
import com.winwintek.web.backend.api.dto.system.UserDTO;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ApiAuditService apiAuditService;
    /*
      com.admin.service.MemberService의 모든 메서드에 대해 적용한다.
    */
    //@Around("execution(* com.admin.service.MemberService.*(..))")

    /*
      com..service 경로에 있는 모든 ~Service.java의 update~로 시작하는 메서드에 대해 적용한다.
    */
    @Around("execution(* com.winwintek.web.backend.api.domain..*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        long startTime = System.currentTimeMillis();
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();
        Object result = null;

        String paramsString = WebUtil.getArgumentsAsString(pjp.getArgs());
        String reqPayload = logRequestPayload(request);

        if (authentication.getName().equals(GlobalEnum.AnomyousUser_String.toString())) {
            System.out.println("==> LogAspect Before:: " + pjp.getSignature().getDeclaringTypeName());
            result = pjp.proceed();

            System.out.println("==> LogAspect After:: " + pjp.getSignature().getDeclaringTypeName());
        } else {
            AccountContext principal = (AccountContext) SecurityContextHolder.getContextHolderStrategy().getContext()
                    .getAuthentication().getPrincipal();
            UserDTO userDTO = principal.getUserDTO();
//            System.out.println(
//                    "==> LogAspect Before:: [ " + userDTO.getUserId() + " ( " + userDTO.getUserTsid() + " ) ]" + pjp.getSignature()
//                            .getDeclaringTypeName());
            System.out.println(
                    "==> LogAspect Before:: [ " + userDTO.getUserId() + " ]" + pjp.getSignature()
                            .getDeclaringTypeName());
            result = pjp.proceed();

//            System.out.println(
//                    "==> LogAspect After:: [ " + userDTO.getUserId() + " ( " + userDTO.getUserTsid() + " ) ]" + pjp.getSignature()
//                            .getDeclaringTypeName());
            System.out.println(
                    "==> LogAspect After:: [ " + userDTO.getUserId() + " ]" + pjp.getSignature()
                            .getDeclaringTypeName());
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("==> Execution Time: {} ms", executionTime);

        String resPayload = logResponsePayload(result);

        // Save log to database
        saveLogToDatabase(request, response, request.getMethod(), paramsString,
                executionTime, reqPayload, resPayload, authentication);


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
