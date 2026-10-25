package com.winwintek.web.backend.api.domain.system.roles.impl;

import com.winwintek.web.backend.api.domain.system.roles.service.RolesService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.system.roles.RoleResponseDetailDTO;
import com.winwintek.web.backend.api.dto.system.roles.RolesCreResDTO;
import com.winwintek.web.backend.api.dto.system.roles.RolesResponseDTO;
import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import com.winwintek.web.backend.api.entity.system.menuMng.MenuEntity;
import com.winwintek.web.backend.api.entity.system.roles.RolesEntity;
import com.winwintek.web.backend.api.repository.system.menuMng.MenuMngRepository;
import com.winwintek.web.backend.api.repository.system.roles.RolesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class RolesServiceImpl implements RolesService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RolesRepository roleRepository;

    @Autowired
    private MenuMngRepository menuRepository;

    @Autowired
    private CommonService commonService;


    /**
     * 권환 관리 등록
     *
     * @param rolesCreResDTO
     * @return
     */
    @Override
    public RolesResponseDTO createRoles(RolesCreResDTO rolesCreResDTO) {
        RolesEntity roleEntity = modelMapper.map(rolesCreResDTO, RolesEntity.class);
        String randomString = ThreadLocalRandom.current().ints(5, 65, 91)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        roleEntity.setRlCd(randomString);

        Set<MenuEntity> menus = new HashSet<>();
        if (rolesCreResDTO.getMenus() != null & rolesCreResDTO.getMenus().size() > 0) {
            menus = new HashSet<>(menuRepository.findAllById(rolesCreResDTO.getMenus()));
        }

        roleEntity.setMenus(menus);
        return modelMapper.map(roleRepository.save(roleEntity), RolesResponseDTO.class);
    }

    /**
     * 권환 관리 목록
     *
     * @return
     */
    @Override
    public Page<RolesResponseDTO> getRoles(PageableDto pageDto) {
        int page = pageDto.getPage();
        int size = pageDto.getSize();
        String sort = pageDto.getSort();

        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<RolesEntity> list = roleRepository.findAll(pageable);
        Page<RolesResponseDTO> listDto = list.map(item -> modelMapper.map(item, RolesResponseDTO.class));
        return listDto;
    }

    /**
     * Update role by id
     *
     * @param rolesCreResDTO
     * @return
     */
    @Override
    public RolesResponseDTO updateRoles(RolesCreResDTO rolesCreResDTO) {
        RolesEntity rolesEntity = roleRepository.findById(rolesCreResDTO.getRlId()).orElseThrow();

        rolesEntity.setRlNm(rolesCreResDTO.getRlNm());
        rolesEntity.setRlDesc(rolesCreResDTO.getRlDesc());
        rolesEntity.setCategory(rolesCreResDTO.getCategory());

        Set<MenuEntity> existingMenus = rolesEntity.getMenus();
        Set<MenuEntity> newMenus = new HashSet<>();

        if (rolesCreResDTO.getMenus() != null & rolesCreResDTO.getMenus().size() > 0) {
            newMenus = new HashSet<>(menuRepository.findAllById(rolesCreResDTO.getMenus()));
        }
        existingMenus.retainAll(newMenus);
        existingMenus.addAll(newMenus);

        return modelMapper.map(rolesEntity, RolesResponseDTO.class);
    }

    /**
     * Get role by id
     *
     * @param id
     * @return
     */
    @Override
    public RoleResponseDetailDTO findRolesById(Long id) {
        RolesEntity rolesEntity = roleRepository.findById(id).orElseThrow();

        return modelMapper.map(rolesEntity, RoleResponseDetailDTO.class);
    }

}
