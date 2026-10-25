package com.alice.hotel.api.config.security.provider;

import com.alice.hotel.api.common.GlobalEnum;
import com.alice.hotel.api.config.security.dto.AccountContext;
import com.alice.hotel.api.config.security.service.FormUserDetailsService;
import com.alice.hotel.api.config.security.token.RestAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.alice.hotel.api.common.Constants.*;

@Component("restAuthenticationProvider")
@RequiredArgsConstructor
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final FormUserDetailsService formUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RestAuthenticationToken token = (RestAuthenticationToken) authentication;
        Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
        String loginDiv = (String)details.get("loginDiv");
        boolean sso = (boolean)details.get("sso");
        String userId = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        UserDetails userDetails = formUserDetailsService.loadUserByUsername(userId, loginDiv);
//        UserDetails userDetails = formUserDetailsService.loadUserByUsername(userId);
        AccountContext accountContext = (AccountContext) userDetails;

        if (sso) {
            throw new AuthenticationServiceException("SSO login is temporarily disabled");
        }

        if (!passwordEncoder.matches(password, accountContext.getUserDTO().getPassword())) {
            throw new AuthenticationServiceException("Invalid password userId : " + userId);
        }
        accountContext.getUserDTO().setPassword("");

        return new RestAuthenticationToken(accountContext.getAuthorities(), accountContext, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(RestAuthenticationToken.class);
    }
}
