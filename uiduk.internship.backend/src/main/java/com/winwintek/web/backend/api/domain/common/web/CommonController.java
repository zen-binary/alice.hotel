package com.winwintek.web.backend.api.domain.common.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.common.nav.NavTreeDto;
import com.winwintek.web.backend.api.dto.common.nav.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/cmm")
@RequiredArgsConstructor
public class CommonController {

    @Autowired
    private CommonService commonService;

    @PostMapping ("/navitems")
    public ApiResponse<RoleDto> getNavItems() {
        RoleDto roleDTO = commonService.getNavItems();

        return new ApiResponse<>(ApiStatus.SUCCESS, roleDTO);
    }

    @PostMapping ("/navTree")
    public ApiResponse<List<NavTreeDto>> getNavTree() {
        List<NavTreeDto> navs = commonService.getNavTreeItems();

        return new ApiResponse<>(ApiStatus.SUCCESS, navs);
    }

}
