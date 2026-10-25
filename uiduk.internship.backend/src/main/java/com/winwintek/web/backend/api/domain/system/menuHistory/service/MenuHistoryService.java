package com.winwintek.web.backend.api.domain.system.menuHistory.service;


import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisRequestDTO;
import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisResDTO;
import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisSearchDTO;
import org.springframework.data.domain.Page;

public interface MenuHistoryService {

    public String createLogMenuHistory(MenuHisRequestDTO menuHis);

    Page<MenuHisResDTO> logMenuHistoryList(MenuHisSearchDTO menuHis);

}
