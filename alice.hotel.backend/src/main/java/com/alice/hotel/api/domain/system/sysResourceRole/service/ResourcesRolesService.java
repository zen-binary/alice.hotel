package com.alice.hotel.api.domain.system.sysResourceRole.service;

import com.alice.hotel.api.dto.system.sysResource.ResourcesResponseDTO;
import com.alice.hotel.api.dto.system.sysResourceRole.ResourcesRolesRequestDTO;
import com.alice.hotel.api.dto.system.sysResourceRole.ResourcesRolesResponseDTO;

import java.util.List;

public interface ResourcesRolesService {

    List<ResourcesRolesResponseDTO> findResourceRoleById(Long roleId);

    List<ResourcesResponseDTO> createResourceRole(ResourcesRolesRequestDTO dto);

}
