package com.winwintek.web.backend.api.config.security.mapper;

import com.winwintek.web.backend.api.repository.system.IResourceRepository;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PersistentUrlRoleMapper implements IUrlRoleMapper {
    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();
    private final IResourceRepository iResourceRepository;

    @Override
    public Map<String, String> getUrlRoleMappings() {
//        urlRoleMappings.clear();
//        urlRoleMappings.put("/api/verify/router/check.do", "permitAll");

        List<Map<String, Object>> allResources = iResourceRepository.findAllResources();

        allResources.stream().forEach(re ->{
            String roleExpression = String.valueOf(re.get("roles")  );

            roleExpression = String.join(" or ",
                    Arrays.stream(roleExpression.split(","))
                            .map(s -> s.trim().equals("permitAll") ? "permitAll" : "hasRole('" + s + "')")
                            .toArray(String[]::new));
            urlRoleMappings.put(String.valueOf(re.get("url")), roleExpression);
        });
        // TODO KhoiNM: test Anomyous User
        urlRoleMappings.put("/files/**", "permitAll");
        urlRoleMappings.put("/auth/api/resourceRoleReset", "permitAll");
        urlRoleMappings.put("/a/auth/**", "permitAll");
//        urlRoleMappings.put("/cmm/navTree", "permitAll");
        urlRoleMappings.put("/cmm/menu-log", "permitAll");
        urlRoleMappings.put("/cmm/files/fileUploadedInfo", "permitAll");
        urlRoleMappings.put("/cmm/files/download", "permitAll");
        urlRoleMappings.put("/auth/logout", "permitAll");
        urlRoleMappings.put("/c/corporate-register", "permitAll");
        urlRoleMappings.put("/c/corporate-register/check-register", "permitAll");
        urlRoleMappings.put("/c/corporate-register/list-code", "permitAll");
        urlRoleMappings.put("/cmm/files/upload", "permitAll");
        urlRoleMappings.put("/auth/impersonate", "permitAll");
        urlRoleMappings.put("/c/public/notice/**", "permitAll");
        urlRoleMappings.put("/cmm/files/downloadMultiple", "permitAll");

        return urlRoleMappings;
    }
}

