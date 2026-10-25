package com.winwintek.web.backend.api.config.security.manager;

import com.winwintek.web.backend.api.config.security.mapper.PersistentUrlRoleMapper;
import com.winwintek.web.backend.api.config.security.service.DynamicAuthorizationService;
import com.winwintek.web.backend.api.config.security.service.SecurityService;
import com.winwintek.web.backend.api.repository.system.IResourceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomDynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final AuthorizationDecision DENY = new AuthorizationDecision(false);

    private static final AuthorizationDecision ACCESS = new AuthorizationDecision(true);

    private final SecurityService securityService;

    private final HandlerMappingIntrospector handlerMappingIntrospector;

    private final IResourceRepository iResourceRepository;

    DynamicAuthorizationService dynamicAuthorizationService;

    private List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;

    @Value ("${spring.profiles.active}")
    private String profilesValue;

    @PostConstruct
    public void mapping() {

        dynamicAuthorizationService = new DynamicAuthorizationService(new PersistentUrlRoleMapper(iResourceRepository));
//                new DynamicAuthpackage com.winwintek.web.backend.api.config.security.manager;

        setMapping();
    }

    private void setMapping() {
        mappings = dynamicAuthorizationService.getUrlRoleMappings().entrySet().stream()
                .map(entry -> new RequestMatcherEntry<>(
                        new MvcRequestMatcher(handlerMappingIntrospector, entry.getKey()),
                        customAuthorizationManager(entry.getValue()))).collect(Collectors.toList());
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext request) {

        //        for (RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : this.mappings) {
//
//            RequestMatcher matcher = mapping.getRequestMatcher();
//            RequestMatcher.MatchResult matchResult = matcher.matcher(request.getRequest());
//            if (matchResult.isMatch()) {
//                AuthorizationManager<RequestAuthorizationContext> manager = mapping.getEntry();
//
//                return manager.check(authentication,
//                        new RequestAuthorizationContext(request.getRequest(), matchResult.getVariables()));
//            }
//        }
//
//        return ACCESS;

        Iterator var3 = this.mappings.iterator();

        RequestMatcherEntry mapping;
        RequestMatcher.MatchResult matchResult;
        do {
            if (! var3.hasNext()) {
                return DENY;
            }

            mapping = (RequestMatcherEntry) var3.next();
            RequestMatcher matcher = mapping.getRequestMatcher();
            matchResult = matcher.matcher(request.getRequest());
        } while (! matchResult.isMatch());

        AuthorizationManager<RequestAuthorizationContext> manager = (AuthorizationManager) mapping.getEntry();

        return manager.check(authentication,
                new RequestAuthorizationContext(request.getRequest(), matchResult.getVariables()));

    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    private AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager(
            String role) {//추후 권한 계층 구조시 해당 메소드 수정필요
        if (role.startsWith("ROLE")) {
            return AuthorityAuthorizationManager.hasRole(role);
//            return AuthorityAuthorizationManager.hasAuthority(role);
//            return AuthorityAuthorizationManager.hasAnyRole(role);
        } else {
            return new WebExpressionAuthorizationManager(role);
        }
    }

    public synchronized void reload() {//동시적 이슈를 위하여 synchronized 설정
        mappings.clear();
        setMapping();
    }

}
