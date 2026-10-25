package com.winwintek.web.backend.api.config.security.filter;


import com.winwintek.web.backend.api.config.security.token.RestAuthenticationToken;
import com.winwintek.web.backend.api.config.security.util.WebUtil;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.winwintek.web.backend.api.common.Constants.*;

@Slf4j
public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();


    public RestAuthenticationFilter(HttpSecurity http) {
        super(new AntPathRequestMatcher("/auth/login", "POST"));
        setSecurityContextRepository(getSecurityContextRepository(http));
    }

    public SecurityContextRepository getSecurityContextRepository(HttpSecurity http) {
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
        if (securityContextRepository == null) {
            securityContextRepository = new DelegatingSecurityContextRepository(
                    new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
        }
        return securityContextRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAsync(request)) {
            throw new IllegalArgumentException("Authentication method not supported");
        }
        UserDTO userDTO= objectMapper.readValue(request.getReader(), UserDTO.class);

        if (!StringUtils.hasText(String.valueOf(userDTO.getUserId()))
            //   || !StringUtils.hasText(accountDto.getPassword())
        ) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }else if(!StringUtils.hasText(userDTO.getLoginDiv())){
            throw new AuthenticationServiceException("loginDiv not provided");
        }
        String pw = "";
        if(userDTO.getLoginDiv().equals(USER_DIV_OUT_CHECK)){
            pw = userDTO.getPassword();
        }else {
            pw = PW_DEAULT;
        }

        Map<String, Object> details = new HashMap<>();
        details.put("loginDiv", userDTO.getLoginDiv());
        details.put("sso", userDTO.isSso());

        RestAuthenticationToken token = new RestAuthenticationToken(userDTO.getUserId(), pw, details);
//        RestAuthenticationToken token = new RestAuthenticationToken(userDTO.getUserId(), userDTO.getPassword(), userDTO.getLoginDiv());

        return this.getAuthenticationManager().authenticate(token);
    }
}
