package com.alice.hotel.api.domain.system.roles.service;

import com.alice.hotel.api.dto.system.roles.RoleResponseDetailDTO;
import com.alice.hotel.api.dto.system.roles.RolesCreResDTO;
import com.alice.hotel.api.dto.system.roles.RolesResponseDTO;
import com.alice.hotel.api.dto.common.pageable.PageableDto;
import org.springframework.data.domain.Page;

public interface RolesService {

    /**
     * 권환 관리 등록
     *
     * @param rolesCreResDTO
     * @return
     */
    RolesResponseDTO createRoles(RolesCreResDTO rolesCreResDTO);

    /**
     * 권환 관리 목록
     *
     * @return
     */
    Page<RolesResponseDTO> getRoles(PageableDto pageDto);

    /**
     * Update role info
     *
     * @param rolesCreResDTO
     * @return
     */
    RolesResponseDTO updateRoles(RolesCreResDTO rolesCreResDTO);

    /**
     * Get role info
     *
     * @param id
     * @return
     */
    RoleResponseDetailDTO findRolesById(Long id);

}
