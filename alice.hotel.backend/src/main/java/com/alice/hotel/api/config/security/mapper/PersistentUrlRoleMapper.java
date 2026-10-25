package com.alice.hotel.api.config.security.mapper;

import com.alice.hotel.api.repository.system.IResourceRepository;
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
        urlRoleMappings.clear();
        try {
            List<Map<String, Object>> allResources = iResourceRepository.findAllResources();
            allResources.forEach(re -> {
                String roleExpression = String.valueOf(re.get("roles"));
                roleExpression = String.join(" or ",
                        Arrays.stream(roleExpression.split(","))
                                .map(s -> s.trim().equals("permitAll") ? "permitAll" : "hasRole('" + s + "')")
                                .toArray(String[]::new));
                urlRoleMappings.put(String.valueOf(re.get("url")), roleExpression);
            });
        } catch (Exception e) {
            // Empty / not-yet-migrated DB: fall back to hardcoded permitAll base paths
        }
        urlRoleMappings.put("/files/**", "permitAll");
        urlRoleMappings.put("/auth/api/resourceRoleReset", "permitAll");
        urlRoleMappings.put("/a/auth/**", "permitAll");
        urlRoleMappings.put("/auth/login", "permitAll");
        urlRoleMappings.put("/cmm/menu-log", "permitAll");
        urlRoleMappings.put("/cmm/files/fileUploadedInfo", "permitAll");
        urlRoleMappings.put("/cmm/files/download", "permitAll");
        urlRoleMappings.put("/auth/logout", "permitAll");
        urlRoleMappings.put("/cmm/files/upload", "permitAll");
        urlRoleMappings.put("/auth/impersonate", "permitAll");
        urlRoleMappings.put("/cmm/files/downloadMultiple", "permitAll");
        urlRoleMappings.put("/cmm/navTree", "permitAll");
        urlRoleMappings.put("/cmm/navitems", "permitAll");
        // Public client booking catalog / auth helpers / VNPay callbacks
        urlRoleMappings.put("/c/booking/hotel", "permitAll");
        urlRoleMappings.put("/c/booking/room-types", "permitAll");
        urlRoleMappings.put("/c/booking/services", "permitAll");
        urlRoleMappings.put("/c/booking/availability", "permitAll");
        urlRoleMappings.put("/c/booking/otp", "permitAll");
        urlRoleMappings.put("/c/booking/register", "permitAll");
        urlRoleMappings.put("/c/booking/vnpay/return", "permitAll");
        urlRoleMappings.put("/c/booking/vnpay/ipn", "permitAll");
        urlRoleMappings.put("/c/booking/**", "isAuthenticated()");
        // Role 1 = quản trị, role 3 = lễ tân. Khách (role 2) không vào admin.
        String staff = "hasAnyRole('1','3')";
        String admin = "hasRole('1')";
        urlRoleMappings.put("/a/hotel-rooms/**", staff);
        urlRoleMappings.put("/a/room-types/**", staff);
        urlRoleMappings.put("/a/rooms/**", staff);
        urlRoleMappings.put("/a/hotel-services/**", staff);
        urlRoleMappings.put("/a/bookings/**", staff);
        urlRoleMappings.put("/a/front-desk/**", staff);
        urlRoleMappings.put("/a/shifts/**", staff);
        urlRoleMappings.put("/a/dashboard/**", staff);
        urlRoleMappings.put("/a/dashboard", staff);
        urlRoleMappings.put("/a/guests/**", staff);
        urlRoleMappings.put("/a/reviews/**", staff);
        urlRoleMappings.put("/a/hotel/**", admin);
        urlRoleMappings.put("/a/vouchers/**", admin);
        urlRoleMappings.put("/a/rates/**", admin);
        urlRoleMappings.put("/a/reports/**", admin);
        urlRoleMappings.put("/a/menu/**", admin);
        urlRoleMappings.put("/a/resources/**", admin);
        urlRoleMappings.put("/a/resources-roles/**", admin);
        urlRoleMappings.put("/a/role/**", admin);
        urlRoleMappings.put("/a/roles/**", admin);
        urlRoleMappings.put("/a/loginHis/**", admin);
        urlRoleMappings.put("/cmm/**", "isAuthenticated()");

        return urlRoleMappings;
    }
}

