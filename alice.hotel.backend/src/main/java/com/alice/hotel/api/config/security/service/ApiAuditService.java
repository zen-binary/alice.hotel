package com.alice.hotel.api.config.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * API audit is disabled for Alice Hotel demo (SYS_API_USE_HIS not required).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiAuditService {

    public void logApiUsageUser(HttpServletRequest request, HttpServletResponse response, String httpMethod,
            String params, long executionTime, String reqPayload, String resPayload, String userId) {
        log.trace("API audit skipped (user={})", userId);
    }

    public void saveAnonymousLog(HttpServletRequest request, HttpServletResponse response, String httpMethod,
            String params, long executionTime, String reqPayload, String resPayload) {
        log.trace("API audit skipped (anonymous)");
    }
}
