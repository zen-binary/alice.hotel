package com.alice.hotel.api.domain.system.menuHistory.web;

import com.alice.hotel.api.common.ApiResponse;
import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.domain.system.menuHistory.service.MenuHistoryService;
import com.alice.hotel.api.dto.system.menuHistory.MenuHisRequestDTO;
import com.alice.hotel.api.dto.system.menuHistory.MenuHisResDTO;
import com.alice.hotel.api.dto.system.menuHistory.MenuHisSearchDTO;
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
