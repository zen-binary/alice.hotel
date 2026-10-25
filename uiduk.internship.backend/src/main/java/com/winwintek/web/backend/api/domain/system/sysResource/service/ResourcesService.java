package com.winwintek.web.backend.api.domain.system.sysResource.service;

import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesRequestDTO;
import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesResponseDTO;
import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesSearchDTO;
import org.springframework.data.domain.Page;

public interface ResourcesService {

    Page<ResourcesResponseDTO> findResource(ResourcesSearchDTO searchDTO);

    ResourcesResponseDTO detailResource(Long id);

    ResourcesResponseDTO createResource(ResourcesRequestDTO dto);

    ResourcesResponseDTO updateResource(ResourcesRequestDTO dto);

    ResourcesResponseDTO deleteResource(Long id);

}
