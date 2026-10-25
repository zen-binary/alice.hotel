package com.alice.hotel.api.domain.system.sysRole.service;


import com.alice.hotel.api.dto.system.sysRole.RoleSearchDTO;
import com.alice.hotel.api.dto.system.sysRole.RolesResponseDTO;
import org.springframework.data.domain.Page;

public interface SysRolesService {

    Page<RolesResponseDTO> getAllRole(RoleSearchDTO searchDTO);
}
