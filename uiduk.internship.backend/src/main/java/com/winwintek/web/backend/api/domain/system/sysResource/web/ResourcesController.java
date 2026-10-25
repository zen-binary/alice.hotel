package com.winwintek.web.backend.api.domain.system.sysResource.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.system.sysResource.service.ResourcesService;
import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesRequestDTO;
import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesResponseDTO;
import com.winwintek.web.backend.api.dto.system.sysResource.ResourcesSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/resources")
public class ResourcesController {

    @Autowired
    private ResourcesService resourcesService;

    @PostMapping ("/list")
    public ApiResponse<Page<ResourcesResponseDTO>> searchResource(@RequestBody ResourcesSearchDTO searchDTO) {
        Page<ResourcesResponseDTO> searchResults = resourcesService.findResource(searchDTO);

        ApiResponse<Page<ResourcesResponseDTO>> lstResult = new ApiResponse<>(ApiStatus.SUCCESS, searchResults);

        return lstResult;
    }

    @PostMapping ("/")
    public ApiResponse<ResourcesResponseDTO> getResourceById(@RequestBody String id) {
        ResourcesResponseDTO detailResource = resourcesService.detailResource(Long.parseLong(id));

        ApiResponse<ResourcesResponseDTO> result = new ApiResponse<>(ApiStatus.SUCCESS, detailResource);

        return result;
    }

    @PostMapping ("/create")
    public ApiResponse<ResourcesResponseDTO> createMenu(@RequestBody ResourcesRequestDTO dto) {
        ResourcesResponseDTO createResults = resourcesService.createResource(dto);

        ApiResponse<ResourcesResponseDTO> result = new ApiResponse<>(ApiStatus.CREATED, createResults);

        return result;
    }

    @PutMapping ("/update")
    public ApiResponse<ResourcesResponseDTO> updateMenu(@RequestBody ResourcesRequestDTO dto) {
        ResourcesResponseDTO updateResult = resourcesService.updateResource(dto);

        ApiResponse<ResourcesResponseDTO> result = new ApiResponse<>(ApiStatus.SUCCESS, updateResult);

        return result;
    }

    @DeleteMapping ("/")
    public ApiResponse<ResourcesResponseDTO> deleteResourceById(@RequestBody String id) {
        ResourcesResponseDTO deleteResource = resourcesService.deleteResource(Long.parseLong(id));

        ApiResponse<ResourcesResponseDTO> result = new ApiResponse<>(ApiStatus.SUCCESS, deleteResource);

        return result;
    }

}
