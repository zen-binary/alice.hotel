package com.winwintek.web.backend.api.domain.system.sysResourceRole.service;

import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesResponseDTO;
import com.winwintek.web.backend.api.dto.system.sysResourceRole.ResourcesRolesRequestDTO;
import com.winwintek.web.backend.api.dto.system.sysResourceRole.ResourcesRolesResponseDTO;

import java.util.List;

public interface ResourcesRolesService {

    List<ResourcesRolesResponseDTO> findResourceRoleById(Long roleId);

    List<ResourcesResponseDTO> createResourceRole(ResourcesRolesRequestDTO dto);

}
