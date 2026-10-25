package com.winwintek.web.backend.api.domain.system.roles.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.system.roles.service.RolesService;
import com.winwintek.web.backend.api.dto.system.roles.RoleResponseDetailDTO;
import com.winwintek.web.backend.api.dto.system.roles.RolesCreResDTO;
import com.winwintek.web.backend.api.dto.system.roles.RolesResponseDTO;
import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/a/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    /**
     * 권환 관리 등록
     *
     * @param rolesCreResDTO
     * @return
     */
    @PostMapping (value = "/")
    public ApiResponse<RolesResponseDTO> createRoles(@RequestBody RolesCreResDTO rolesCreResDTO) {
        RolesResponseDTO responseDTO = rolesService.createRoles(rolesCreResDTO);

        return new ApiResponse<>(ApiStatus.CREATED, responseDTO);
    }

    /**
     * 권환 관리 목록
     *
     * @return
     */
    @PostMapping ("/list")
    public ApiResponse<Page<RolesResponseDTO>> getAllRoles(@RequestBody PageableDto pageDto) {
        Page<RolesResponseDTO> rolesResponseDTOS = rolesService.getRoles(pageDto);

        return new ApiResponse<>(ApiStatus.SUCCESS, rolesResponseDTOS);
    }

    /**
     * Get role info
     *
     * @param rolesCreResDTO
     * @return
     */
    @PostMapping ("/detail")
    public ApiResponse<RoleResponseDetailDTO> getRolesById(@RequestBody RolesCreResDTO rolesCreResDTO) {
        RoleResponseDetailDTO responseDTO = rolesService.findRolesById(rolesCreResDTO.getRlId());

        return new ApiResponse<>(ApiStatus.SUCCESS, responseDTO);
    }

    /**
     * Update role info
     *
     * @param rolesCreResDTO
     * @return
     */
    @PutMapping ("/")
    public ApiResponse<RolesResponseDTO> updateRoles(@RequestBody RolesCreResDTO rolesCreResDTO) {
        RolesResponseDTO responseDTO = rolesService.updateRoles(rolesCreResDTO);

        return new ApiResponse<>(ApiStatus.CREATED, responseDTO);
    }

}
