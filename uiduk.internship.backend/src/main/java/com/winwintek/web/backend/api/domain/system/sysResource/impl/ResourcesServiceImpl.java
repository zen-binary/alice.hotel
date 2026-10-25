package com.winwintek.web.backend.api.domain.system.sysResource.impl;

import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.domain.system.sysResource.service.ResourcesService;
import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesRequestDTO;
import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesResponseDTO;
import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesSearchDTO;
import com.winwintek.web.backend.api.entity.system.sysResource.IResourcesSearchResponse;
import com.winwintek.web.backend.api.entity.system.sysResource.ResourcesEntity;
import com.winwintek.web.backend.api.repository.system.sysResource.IResourcesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ResourcesServiceImpl implements ResourcesService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IResourcesRepository resourcesRepository;

    @Override
    public Page<ResourcesResponseDTO> findResource(ResourcesSearchDTO searchDTO) {
        int page = searchDTO.getPage();
        int size = searchDTO.getSize();
        String sort = searchDTO.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<IResourcesSearchResponse> lstResults = resourcesRepository.searchResource(searchDTO, pageable);
        Page<ResourcesResponseDTO> lstResultsDTO = lstResults.map(
                item -> modelMapper.map(item, ResourcesResponseDTO.class));

        return lstResultsDTO;
    }

    @Override
    public ResourcesResponseDTO detailResource(Long id) {
        ResourcesEntity entityExist = resourcesRepository.findById(id).orElse(new ResourcesEntity());
        ResourcesResponseDTO resultDTO = modelMapper.map(entityExist, ResourcesResponseDTO.class);

        return resultDTO;
    }

    @Override
    public ResourcesResponseDTO createResource(ResourcesRequestDTO dto) {
        ResourcesEntity entity = modelMapper.map(dto, ResourcesEntity.class);

        entity.setRegDate(new Date());
        ResourcesResponseDTO result = modelMapper.map(resourcesRepository.save(entity), ResourcesResponseDTO.class);

        return result;
    }

    @Override
    public ResourcesResponseDTO updateResource(ResourcesRequestDTO dto) {
        ResourcesEntity entity = new ResourcesEntity();
        Date regDate = null;
        if (dto.getRsId() != null) {
            entity = resourcesRepository.findById(dto.getRsId()).orElse(new ResourcesEntity());
            regDate = entity.getRegDate();
            modelMapper.map(dto, entity);
            entity.setRegDate(regDate);
        }

        ResourcesResponseDTO result = modelMapper.map(resourcesRepository.save(entity), ResourcesResponseDTO.class);

        return result;
    }

    @Override
    public ResourcesResponseDTO deleteResource(Long id) {
        ResourcesEntity entity = resourcesRepository.findById(id).orElse(new ResourcesEntity());

        if (entity.getRsId() != null) {
            resourcesRepository.delete(entity);
        }
        ResourcesResponseDTO result = modelMapper.map(entity, ResourcesResponseDTO.class);

        return result;
    }

}
