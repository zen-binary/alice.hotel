package com.alice.hotel.api.config.security.service;

import com.alice.hotel.api.dto.system.ResultDTO;
import com.alice.hotel.api.dto.system.VerifyCheckUrlDTO;
import com.alice.hotel.api.entity.system.IMenuEntity;
import com.alice.hotel.api.repository.system.IMenuRepository;
import com.alice.hotel.api.repository.system.IResourceRepository;
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
        String requestUrl = verifyCheckUrlDTO == null ? null : verifyCheckUrlDTO.getUrl();

        String[] excludeUrl = {"/login", "/intro", "/error"};
        if (requestUrl == null || requestUrl.isBlank() || Arrays.asList(excludeUrl).contains(requestUrl)) {
            return ResponseEntity.ok(new ResultDTO(requestUrl != null && Arrays.asList(excludeUrl).contains(requestUrl)));
        }

        List<IMenuEntity> menus = iMenuRepository.findByRoute(requestUrl).stream()
                .filter(menu -> menu.getDeleteFlag() == null || !"Y".equalsIgnoreCase(menu.getDeleteFlag()))
                .toList();
        if (menus.isEmpty() || menus.stream().allMatch(menu -> menu.getRoleSet() == null || menu.getRoleSet().isEmpty())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, 401, "Không có quyền truy cập."));
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roleList = authorities.stream().map(GrantedAuthority::getAuthority)
                .flatMap(roles -> Arrays.stream(roles.split(","))).collect(Collectors.toList());

        boolean hasRoleValue = menus.stream()
                .filter(menu -> menu.getRoleSet() != null)
                .flatMap(menu -> menu.getRoleSet().stream())
                .map(role -> "ROLE_" + role.getRlId())
                .anyMatch(roleList::contains);


        if (hasRoleValue) {
            return ResponseEntity.ok(new ResultDTO(hasRoleValue));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResultDTO(userId, 401, "Không có quyền truy cập."));
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
