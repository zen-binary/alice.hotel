package com.alice.hotel.api.config.security.util;

import com.alice.hotel.api.config.security.dto.AccountContext;
import com.alice.hotel.api.dto.system.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AccountContext accountContext) {
            return accountContext.getUserDTO();
        }
        return null;
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AccountContext accountContext) {
            return accountContext.getUsername();
        }
        if (principal instanceof UserDetails details) {
            return details.getUsername();
        }
        String name = String.valueOf(principal);
        return "anonymousUser".equals(name) ? null : name;
    }
}
