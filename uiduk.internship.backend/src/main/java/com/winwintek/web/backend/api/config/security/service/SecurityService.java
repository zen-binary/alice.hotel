package com.winwintek.web.backend.api.config.security.service;

import com.winwintek.web.backend.api.dto.system.ResultDTO;
import com.winwintek.web.backend.api.dto.system.VerifyCheckUrlDTO;
import com.winwintek.web.backend.api.entity.system.IMenuEntity;
import com.winwintek.web.backend.api.repository.system.IMenuRepository;
import com.winwintek.web.backend.api.repository.system.IResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    @Autowired
    private IResourceRepository iResourceRepository;

    @Autowired
    private IMenuRepository iMenuRepository;

    public ResponseEntity<ResultDTO> verifyRouterCheck (VerifyCheckUrlDTO verifyCheckUrlDTO) {
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().getContext();
        Authentication authentication = context.getAuthentication();

        if (null == authentication) {
            return ResponseEntity.ok(new ResultDTO(false));
        }

        String userId = authentication.getName();
        String requestUrl = verifyCheckUrlDTO.getUrl();

        IMenuEntity iMenuEntity = iMenuRepository.findByRoute(requestUrl);

        // 권한 체크 예외 URL
        String[] excludeUrl = {"/login", "/intro", "/error"};
        if (Arrays.asList(excludeUrl).contains(requestUrl)) {
            return ResponseEntity.ok(new ResultDTO(true));
        } else if (iMenuEntity.getRoleSet().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, 401, "권한이 없습니다."));
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roleList = authorities.stream().map(GrantedAuthority::getAuthority)
                .flatMap(roles -> Arrays.stream(roles.split(","))).collect(Collectors.toList());

//        boolean hasRoleValue = true;// TODO verify method, 미완성이니 수정해야한다.
        boolean hasRoleValue = iMenuEntity.getRoleSet().stream().map(role -> "ROLE_" + role.getRlId())
                .anyMatch(roleList::contains);


        if (hasRoleValue) {
            return ResponseEntity.ok(new ResultDTO(hasRoleValue));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDTO(userId, 401, "권한이 없습니다."));
        }
    }

    public Authentication sessionCheck () {
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().getContext();
        Authentication authentication = context.getAuthentication();

        String name = authentication.getName();
        System.out.println(name);
        return authentication;
    }

}
