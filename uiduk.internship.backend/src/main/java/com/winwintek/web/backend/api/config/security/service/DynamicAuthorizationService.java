package com.winwintek.web.backend.api.config.security.service;

import com.winwintek.web.backend.api.config.security.mapper.IUrlRoleMapper;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class DynamicAuthorizationService {
    private final IUrlRoleMapper delegate;

    public Map<String, String> getUrlRoleMappings() {
        return delegate.getUrlRoleMappings();
    }
}
