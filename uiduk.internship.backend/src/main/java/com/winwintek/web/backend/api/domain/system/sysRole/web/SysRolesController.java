package com.winwintek.web.backend.api.domain.system.sysRole.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.system.sysRole.service.SysRolesService;
import com.winwintek.web.backend.api.dto.system.sysRole.RoleSearchDTO;
import com.winwintek.web.backend.api.dto.system.sysRole.RolesResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/role")
public class SysRolesController {

    @Autowired
    private SysRolesService rolesService;

    @PostMapping ("/list")
    public ApiResponse<Page<RolesResponseDTO>> getListRole(@RequestBody RoleSearchDTO searchDTO) {
        Page<RolesResponseDTO> lstResults = rolesService.getAllRole(searchDTO);

        ApiResponse<Page<RolesResponseDTO>> lstResult = new ApiResponse<>(ApiStatus.SUCCESS, lstResults);

        return lstResult;
    }

}
