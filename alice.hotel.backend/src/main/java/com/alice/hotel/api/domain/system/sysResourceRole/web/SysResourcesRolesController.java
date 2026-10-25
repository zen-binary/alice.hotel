package com.alice.hotel.api.domain.system.sysResourceRole.web;

import com.alice.hotel.api.common.ApiResponse;
import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.domain.system.sysResourceRole.service.ResourcesRolesService;
import com.alice.hotel.api.dto.system.sysResource.ResourcesResponseDTO;
import com.alice.hotel.api.dto.system.sysResourceRole.ResourcesRolesRequestDTO;
import com.alice.hotel.api.dto.system.sysResourceRole.ResourcesRolesResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/resources-roles")
public class SysResourcesRolesController {

    @Autowired
    private ResourcesRolesService resourcesRolesService;

    @PostMapping ("/")
    public ApiResponse<List<ResourcesRolesResponseDTO>> findResourceRoleById(@RequestBody String roleId) {
        List<ResourcesRolesResponseDTO> lstResourceRole = resourcesRolesService.findResourceRoleById(
                Long.parseLong(roleId));

        ApiResponse<List<ResourcesRolesResponseDTO>> result = new ApiResponse<>(ApiStatus.SUCCESS, lstResourceRole);

        return result;
    }

    @PostMapping ("/createResourceRole")
    public ApiResponse<ResourcesResponseDTO> createResourceRole(@RequestBody ResourcesRolesRequestDTO dto) {
        resourcesRolesService.createResourceRole(dto);

        ApiResponse<ResourcesResponseDTO> result = new ApiResponse<>(ApiStatus.CREATED, null);
        return result;
    }

}
