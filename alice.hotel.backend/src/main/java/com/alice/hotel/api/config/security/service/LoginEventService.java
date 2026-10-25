package com.alice.hotel.api.config.security.service;

import com.alice.hotel.api.dto.system.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Login audit is disabled for Alice Hotel demo (schema differs from legacy Oracle tables).
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class LoginEventService {

    public void logSuccess(HttpServletRequest request, UserDTO userDTO) {
        log.debug("Login success: {}", userDTO != null ? userDTO.getUserId() : null);
    }

    public void logFailure(HttpServletRequest request, String userId) {
        log.debug("Login failure: {}", userId);
    }
}
