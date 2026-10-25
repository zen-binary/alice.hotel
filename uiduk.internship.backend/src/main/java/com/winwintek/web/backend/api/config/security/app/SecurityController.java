package com.winwintek.web.backend.api.config.security.app;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.config.security.dto.LoginDTO;
import com.winwintek.web.backend.api.config.security.manager.CustomDynamicAuthorizationManager;
import com.winwintek.web.backend.api.config.security.service.FormUserDetailsService;
import com.winwintek.web.backend.api.config.security.service.SecurityService;
import com.winwintek.web.backend.api.dto.system.ResultDTO;
import com.winwintek.web.backend.api.dto.system.VerifyCheckUrlDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.winwintek.web.backend.api.common.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/auth")
public class SecurityController {

    @Autowired
    private CustomDynamicAuthorizationManager authorizationManager;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FormUserDetailsService formUserDetailsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

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
        // TODO redis 데이터삭제
        // SSOTOKEN 삭제
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
     * @param loginDTO information user.
     * @return token
     */
    @PostMapping("/impersonate")
    public ResponseEntity<?> impersonateStaff(@RequestBody LoginDTO loginDTO) {

        try {
            String token = formUserDetailsService.switchToUser(loginDTO.getUserId(), loginDTO.getPassword(),
                    loginDTO.getLoginDiv());
            // save information to  Redis
            redisTemplate.opsForValue().set(token, loginDTO.getUserId());

            // response token
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            if(StringUtils.equals(loginDTO.loginDiv,USER_DIV_ADM_CHECK)){
                response.put("div", USER_DIV_ADM_TOKEN);
            }
            if(StringUtils.equals(loginDTO.loginDiv,USER_DIV_OUT_CHECK)){
                response.put("div", USER_DIV_OUT_TOKEN);
            }

            return ResponseEntity.ok(response);
        } catch (AuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
