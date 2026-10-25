package com.winwintek.web.backend.api.domain.system.menuHistory.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.system.menuHistory.service.MenuHistoryService;
import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisRequestDTO;
import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisResDTO;
import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/")
public class MenuHistoryController {

    @Autowired
    private MenuHistoryService menuHistoryService;

    @PostMapping ("a/menu-history/list")
    public ApiResponse<Page<MenuHisResDTO>> findMenuHistory(@RequestBody MenuHisSearchDTO searchDTO) {
        Page<MenuHisResDTO> his = menuHistoryService.logMenuHistoryList(searchDTO);
        return new ApiResponse<>(ApiStatus.SUCCESS, his);
    }

    @PostMapping ("cmm/menu-log")
    public ApiResponse<String> createMenuHis(@RequestBody MenuHisRequestDTO dto) {
        String connHisId = menuHistoryService.createLogMenuHistory(dto);
        return new ApiResponse<>(ApiStatus.CREATED, dto.getRoute());
    }
}
