package com.winwintek.web.backend.api.config.security.util;

import com.winwintek.web.backend.api.config.security.dto.AccountContext;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        return accountContext.getUserDTO();
    }
}
