package com.winwintek.web.backend.api.domain.system.sysResourceRole.impl;

import com.winwintek.web.backend.api.domain.system.sysResourceRole.service.ResourcesRolesService;
import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesResponseDTO;
import com.winwintek.web.backend.api.dto.system.sysResourceRole.ResourcesRolesRequestDTO;
import com.winwintek.web.backend.api.dto.system.sysResourceRole.ResourcesRolesResponseDTO;
import com.winwintek.web.backend.api.entity.system.sysResource.ResourcesEntity;
import com.winwintek.web.backend.api.entity.system.sysResourceRole.IResourcesRolesResponse;
import com.winwintek.web.backend.api.entity.system.sysResourceRole.ResourcesRolesEntity;
import com.winwintek.web.backend.api.entity.system.sysRole.SysRolesEntity;
import com.winwintek.web.backend.api.repository.system.sysResource.IResourcesRepository;
import com.winwintek.web.backend.api.repository.system.sysResourceRole.IResourcesRolesRepository;
import com.winwintek.web.backend.api.repository.system.sysRole.IRolesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourcesRoleServiceImpl implements ResourcesRolesService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRolesRepository rolesRepository;

    @Autowired
    private IResourcesRepository resourcesRepository;

    @Autowired
    private IResourcesRolesRepository resourcesRolesRepository;

    @Override
    public List<ResourcesRolesResponseDTO> findResourceRoleById(Long roleId) {
        List<IResourcesRolesResponse> lstData = resourcesRolesRepository.findByRoleIdAndResourceId(roleId, null);
        List<ResourcesRolesResponseDTO> lstResults = lstData.stream()
                .map(item -> modelMapper.map(item, ResourcesRolesResponseDTO.class)).toList();

        return lstResults;
    }

    @Override
    public List<ResourcesResponseDTO> createResourceRole(ResourcesRolesRequestDTO dto) {
        List<ResourcesResponseDTO> lstResults = new ArrayList<>();

        if (dto != null) {
            SysRolesEntity rolesEntity = rolesRepository.findById(Long.parseLong(dto.getRoleId()))
                    .orElse(new SysRolesEntity());

            if (rolesEntity.getRlId() != null && ! dto.getListInsert().isEmpty()) {
                for (String resourceId : dto.getListInsert()) {
                    ResourcesEntity resourceEntity = resourcesRepository.findById(Long.parseLong(resourceId))
                            .orElse(new ResourcesEntity());

                    if (resourceEntity.getRsId() != null) {
                        ResourcesRolesEntity entity = new ResourcesRolesEntity();
                        entity.setRlId(rolesEntity.getRlId());
                        entity.setRsId(resourceEntity.getRsId());

                        resourcesRolesRepository.save(entity);
                        lstResults.add(modelMapper.map(entity, ResourcesResponseDTO.class));
                    }
                }
            }

            if (! dto.getListDelete().isEmpty()) {
                for (String resourceId : dto.getListDelete()) {
                    List<ResourcesRolesEntity> lstInDb = resourcesRolesRepository.findByRoleIdAndResourceId(
                                    Long.parseLong(dto.getRoleId()), Long.parseLong(resourceId)).stream()
                            .map(item -> modelMapper.map(item, ResourcesRolesEntity.class)).toList();

                    if (! lstInDb.isEmpty()) {
                        resourcesRolesRepository.delete(lstInDb.get(0));
                    }
                }
            }
        }

        return lstResults;
    }

}
