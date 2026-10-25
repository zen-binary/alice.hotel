package com.winwintek.web.backend.api.domain.system.sysRole.service;


import com.winwintek.web.backend.api.dto.system.sysRole.RoleSearchDTO;
import com.winwintek.web.backend.api.dto.system.sysRole.RolesResponseDTO;
import org.springframework.data.domain.Page;

public interface SysRolesService {

    Page<RolesResponseDTO> getAllRole(RoleSearchDTO searchDTO);
}
