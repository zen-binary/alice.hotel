package com.alice.hotel.api.config.security.app;

import com.alice.hotel.api.config.security.dto.LoginDTO;
import com.alice.hotel.api.config.security.manager.CustomDynamicAuthorizationManager;
import com.alice.hotel.api.config.security.service.SecurityService;
import com.alice.hotel.api.dto.system.ResultDTO;
import com.alice.hotel.api.dto.system.VerifyCheckUrlDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/auth")
public class SecurityController {

    @Autowired
    private CustomDynamicAuthorizationManager authorizationManager;

    @Autowired
    private SecurityService securityService;

    @GetMapping ("/router/check.do")
    public ResponseEntity<ResultDTO> verifyRouterCheck(@ModelAttribute VerifyCheckUrlDTO verifyCheckUrlDTO) {
        return securityService.verifyRouterCheck(verifyCheckUrlDTO);
    }

    @GetMapping (value = "/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("in");
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        // SSO/Redis temporarily disabled — clear legacy SSOTOKEN cookie if present
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SSOTOKEN")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(true));
    }

    @PostMapping (value = "/api/resourceRoleReset")
    public String resourceRoleReset() {
        authorizationManager.reload();
        return "resourceRoleReset success";
    }

    @GetMapping (value = "/sessionCheck")
    public ResponseEntity sessionCheck() {
        return ResponseEntity.ok(new ResultDTO<>(securityService.sessionCheck()));
    }

    /**
     * SSO/impersonate via Redis is temporarily disabled.
     * Use direct form login on admin FE ({@code /auth/login}) instead.
     */
    @PostMapping("/impersonate")
    public ResponseEntity<?> impersonateStaff(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("SSO login is temporarily disabled. Please sign in directly on the admin site.");
    }
}
