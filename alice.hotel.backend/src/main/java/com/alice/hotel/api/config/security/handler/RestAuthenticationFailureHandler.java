package com.alice.hotel.api.config.security.handler;

import com.alice.hotel.api.config.security.dto.AccountContext;
import com.alice.hotel.api.config.security.service.LoginEventService;
import com.alice.hotel.api.dto.system.ResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alice.hotel.api.dto.system.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component("restFailureHandler")
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final LoginEventService loginEventService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        int startIndex = exception.toString().indexOf("userId : ");
        String userId = "";
        if (startIndex != -1) {
            userId = exception.toString().substring(startIndex + "userId : ".length());
        }
        loginEventService.logFailure(request, userId);

//        if (exception instanceof BadCredentialsException) {
//            mapper.writeValue(response.getWriter(), "Invalid username or password");
//        }

//        ResultDTO resultDTO = new ResultDTO(null, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

        mapper.writeValue(response.getWriter(), new ResultDTO(null, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()));
    }
}
