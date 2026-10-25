package com.alice.hotel.api.system;


import com.alice.hotel.api.config.security.service.FormUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.alice.hotel.api.common.Constants.ROLE_CORPORATE;
import static com.alice.hotel.api.common.Constants.ROLE_STUDENT;

@Slf4j
@Component
public class CommonInterceptor implements HandlerInterceptor {

    @Autowired
    private FormUserDetailsService formUserDetailsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        log.info(" * Request URI : " + request.getRequestURI());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isStudent = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ROLE_STUDENT));

        if (isStudent) {
            Boolean agreeToPolicy = formUserDetailsService.loadAgreeToPolicyStudent(auth.getName());
            if (! agreeToPolicy) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("You must agree to the policy to use the system.");
                return false;
            }
        }

        boolean isCorm = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ROLE_CORPORATE));

        if (isCorm) {
            Boolean agreeToPolicy = formUserDetailsService.loadAgreeCorm(auth.getName());
            if (! agreeToPolicy) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("You must agree to the policy to use the system.");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

}