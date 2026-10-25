package com.winwintek.web.backend.api.config.security.mapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBasedUrlRoleMapper implements IUrlRoleMapper {

    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();
    @Override
    public Map<String, String> getUrlRoleMappings() {
        urlRoleMappings.put("/api/**", "permitAll");
        urlRoleMappings.put("/api/testB", "hasRole('3') or hasRole('4')");
        urlRoleMappings.put("/api/testC", "ROLE_3");

        return urlRoleMappings;
    }
}
