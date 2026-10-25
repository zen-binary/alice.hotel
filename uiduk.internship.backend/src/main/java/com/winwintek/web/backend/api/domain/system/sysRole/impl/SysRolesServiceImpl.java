package com.winwintek.web.backend.api.domain.system.sysRole.impl;

import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.domain.system.sysRole.service.SysRolesService;
import com.winwintek.web.backend.api.dto.system.sysRole.RoleSearchDTO;
import com.winwintek.web.backend.api.dto.system.sysRole.RolesResponseDTO;
import com.winwintek.web.backend.api.entity.system.sysRole.SysRolesEntity;
import com.winwintek.web.backend.api.repository.system.sysRole.IRolesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SysRolesServiceImpl implements SysRolesService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRolesRepository rolesRepository;

    @Override
    public Page<RolesResponseDTO> getAllRole(RoleSearchDTO searchDTO) {
        int page = searchDTO.getPage();
        int size = searchDTO.getSize();
        String sort = searchDTO.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<SysRolesEntity> lstResults = rolesRepository.findAll(pageable);
        Page<RolesResponseDTO> lstResultsDTO = lstResults.map(item -> modelMapper.map(item, RolesResponseDTO.class));

        return lstResultsDTO;
    }

}
